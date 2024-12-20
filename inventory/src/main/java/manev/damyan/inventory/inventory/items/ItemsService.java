package manev.damyan.inventory.inventory.items;

import brave.internal.extra.MapExtra;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import manev.damyan.inventory.inventory.config.kafka.KafkaConfig;
import manev.damyan.inventory.inventory.inventory.cache.RedisInventoryItem;
import manev.damyan.inventory.inventory.inventory.cache.RedisItemRepository;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.HeaderMapper;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemsService {

    public static final String ITEMS_CACHE = "items-cache";

    public static final String ALL = "'all'";

    private final ItemRepository itemRepository;

    private final RedisItemRepository redisItemRepository;

    private final ItemMapper mapper;

    private final KafkaTemplate kafkaTemplate;

    private final ObjectMapper jsonMapper;

    @Transactional //this is needed, because of the pessimistic locking
    //    @CacheEvict(cacheNames = ITEMS_CACHE, key = ALL)
    public boolean update(ItemDTO dto) throws JsonProcessingException {
        boolean existing = itemRepository.findByIdWithPessimisticLock(dto.getId()).isPresent();
        Optional<Item> oldItemEntity = itemRepository.findById(dto.getId());
        ItemDTO oldItemDTO = oldItemEntity.isPresent() ? mapper.convertToDTO(oldItemEntity.get()) : null;
        itemRepository.save(mapper.convertToEntity(dto));

        String reportJson = jsonMapper.writeValueAsString(new ItemReportingDTO("update", oldItemDTO, dto));
        Message<String> producerRecord = MessageBuilder.withPayload(reportJson)

                .setHeader("SourceApp", "Inventory").build();

        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(KafkaConfig.ITEMS_TOPIC, String.valueOf(dto.getId()), producerRecord);
        send.whenComplete(completetionKafkaReporter(dto.getId()));

        return existing;
    }

    @Transactional //this is needed, because of the pessimistic locking
    //    @CacheEvict(cacheNames = ITEMS_CACHE, key = ALL)
    public ItemDTO create(ItemDTO dto) throws JsonProcessingException {

        Item savedItem = itemRepository.save(mapper.convertToEntity(dto));

        String reportJson = jsonMapper.writeValueAsString(new ItemReportingDTO("create", null, dto));

        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaConfig.ITEMS_TOPIC, null, String.valueOf(savedItem.getId()), reportJson,
                                                                     Arrays.asList(new RecordHeader("SourceApp", "Inventory".getBytes())));
        CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(record);
        send.whenComplete(completetionKafkaReporter(savedItem.getId()));

        //        redisItemRepository.save(
        //                new RedisInventoryItem(savedItem.getId(), savedItem.getName(), savedItem.getType(), savedItem.getDetailedDescription()));
        return mapper.convertToDTO(savedItem);
    }
    //    @CacheEvict(cacheNames = ITEMS_CACHE, key = ALL)

    @Transactional
    public boolean deleteItem(long id) throws JsonProcessingException {
        if (!itemRepository.findByIdWithPessimisticLock(id).isPresent()) {
            return false;
        } else {
            Optional<Item> oldItemEntity = itemRepository.findById(id);
            ItemDTO oldItemDTO = oldItemEntity.isPresent() ? mapper.convertToDTO(oldItemEntity.get()) : null;
            String reportJson = jsonMapper.writeValueAsString(new ItemReportingDTO("delete", oldItemDTO, null));
            CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(KafkaConfig.ITEMS_TOPIC, String.valueOf(id), reportJson);
            send.whenComplete(completetionKafkaReporter(id));

            itemRepository.deleteById(id);
            return true;
        }
    }

    @Loggable
    //    @Cacheable(cacheNames = ITEMS_CACHE, key = ALL)
    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream().map(mapper::convertToDTO).collect(Collectors.toList());
    }

    public Page<ItemDTO> getPaginated(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size,
                                                 Sort.by(Sort.Direction.ASC, sortBy).and(Sort.by(Sort.Direction.ASC, "detailedDescription")));
        return itemRepository.findAll(pageRequest).map(mapper::convertToDTO);
    }

    @Transactional //this is needed, because of the pessimistic locking
    public Optional<ItemDTO> getItem(long id) throws JsonProcessingException {
        log.debug("Inside getItemId");
        Optional<Item> entity = itemRepository.findById(id);
        if (!entity.isPresent()) {
            return Optional.empty();
        } else {
            ItemDTO value = mapper.convertToDTO(entity.get());

            String reportJson = jsonMapper.writeValueAsString(new ItemReportingDTO("get", value, null));
            CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(KafkaConfig.ITEMS_TOPIC, String.valueOf(id), reportJson);
            send.whenComplete(completetionKafkaReporter(id));

            value.getName();
            return Optional.of(value);

        }

        //        Optional<RedisInventoryItem> redisItem = redisItemRepository.findById(id);
        //
        //        if (redisItem.isPresent()) {
        //            RedisInventoryItem redisInventoryItem = redisItem.get();
        //            return Optional.of(new ItemDTO(redisInventoryItem.getId(), redisInventoryItem.getName(), redisInventoryItem.getType(),
        //                                           redisInventoryItem.getDetailedDescription()));
        //        } else {
        //            Optional<Item> entity = itemRepository.findById(id);
        //            if (!entity.isPresent()) {
        //                return Optional.empty();
        //            } else {
        //                ItemDTO value = mapper.convertToDTO(entity.get());
        //                value.getName();
        //                return Optional.of(value);
        //
        //            }
        //
        //        }

    }

    private static BiConsumer<SendResult<String, String>, Throwable> completetionKafkaReporter(long id) {
        return (result, e) -> {

            Logger testLogger = LoggerFactory.getLogger("manev.damyan.inventory.inventory.config.kafka");
            if (e == null) {
                testLogger.info(
                        "Sending event for " + id + " completed!. The event send is: " + result.getProducerRecord() + " and the metadata is: " +
                                result.getRecordMetadata());

            } else {
                testLogger.info("Exception while sending event for : " + id + "!");
            }
        };
    }

    public List<ItemDTO> getAllByName(String name) {
        List<RedisInventoryItem> item = redisItemRepository.findByName(name);
        if (!item.isEmpty()) {
            return item.stream().map(ri -> new ItemDTO(ri.getId(), ri.getName(), ri.getType(), ri.getDetailedDescription())).toList();
        } else {
            return itemRepository.findByName(name).stream().map(mapper::convertToDTO).toList();
        }
    }

    public Optional<List<ItemDTO>> getAllByNameInsensitive(String nameInsensitive) {
        return itemRepository.findByNameIgnoreCase(nameInsensitive).map(entities -> entities.stream().map(mapper::convertToDTO).toList());
    }

    public List<ItemDTO> deleteByName(String name) {
        return itemRepository.deleteByName(name).stream().map(mapper::convertToDTO).toList();
    }

    public List<ItemDTO> getAllItemsByNameWithShortRepresentation(String name, Long id) {
        return itemRepository.findByNameAndId(name, id).stream().map(projection -> {
            ItemDTO responseDTO = new ItemDTO();

            responseDTO.setId(projection.getId());
            responseDTO.setName(projection.getName());
            return responseDTO;
        }).toList();
    }

    public Optional<ItemDTO> fake() {
        if (1 == 1) {
            throw new IllegalArgumentException("Exception thrown in FAKE method (that's expected)");
        }

        return Optional.empty();
    }
}
