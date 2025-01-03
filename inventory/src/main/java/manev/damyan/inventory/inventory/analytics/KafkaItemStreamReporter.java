package manev.damyan.inventory.inventory.analytics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import manev.damyan.inventory.inventory.config.kafka.KafkaConfig;
import manev.damyan.inventory.inventory.items.ItemDTO;
import manev.damyan.inventory.inventory.items.Loggable;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

@Slf4j
@Service
@Data
@Loggable
public class KafkaItemStreamReporter {

    private static final String INPUT_TOPIC = KafkaConfig.ITEMS_TOPIC;

    private static final String OUTPUT_ITEM_OPERATIONS_TOPIC = KafkaConfig.ITEMS_ANALYTICS_ACTION_TOPIC;

    private static final String OUTPUT_ITEM_OPERATIONS_AGGREGATION_TOPIC = KafkaConfig.ITEMS_ANALYTICS_AGGREGATION_ACTION_TOPIC;

    private final ObjectMapper mapper;

    //    @EventListener(ApplicationReadyEvent.class)
    public void startStream() throws IOException, InterruptedException {
        Properties streamConfig = new Properties();
        streamConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "InventoryAppTestStream");
        streamConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamConfig.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamConfig.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        streamConfig.put(StreamsConfig.STATE_DIR_CONFIG, Files.createTempDirectory("dpm-kafka-streams").toAbsolutePath().toString());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> operationReporterStream = builder.stream(INPUT_TOPIC);

        KStream<String, String> operationOutput = operationReporterStream.peek(
                (k, v) -> log.info(String.format("Message in stream: [key, value]: [%1s, %2s]", k, v))).filter((k, v) -> {
            try {
                return !"update".equals(mapper.readValue(v, ItemReportingDTO.class).getType());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).peek((k, v) -> log.info(String.format("Message has passed the filter: [key, value]: [%1s, %2s]", k, v))).mapValues(x -> {
            try {
                ItemReportingDTO inputItem = mapper.readValue(x, ItemReportingDTO.class);

                ItemDTO itemDTO = inputItem.getOldDTO() != null ? inputItem.getOldDTO() : inputItem.getNewDTO();
                ItemOperationReportingDTO result = new ItemOperationReportingDTO(itemDTO.getId(), inputItem.getType());
                return mapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        // change the types, so we are sending types, not strings
        // add filter here
        operationOutput.to(OUTPUT_ITEM_OPERATIONS_TOPIC, Produced.with(Serdes.String(), Serdes.String()));

        Topology topology = builder.build();

        KafkaStreams kafkaStreams = new KafkaStreams(topology, streamConfig);
        kafkaStreams.start();

    }

    @EventListener(ApplicationReadyEvent.class)
    public void startKTableStream() throws IOException, InterruptedException {
        Properties streamConfig = new Properties();
        streamConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "InventoryAppKtableTest");
        streamConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamConfig.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamConfig.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        streamConfig.put(StreamsConfig.STATE_DIR_CONFIG, Files.createTempDirectory("dpm-kafka-ktable").toAbsolutePath().toString());

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> operationReporterStream = builder.stream(INPUT_TOPIC);

        KTable<String, Long> ktable = operationReporterStream.groupBy((k, v) -> {
            try {
                String operationType = mapper.readValue(v, ItemReportingDTO.class).getType();

                return k + "," + operationType;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).count(Materialized.as("ItemOperationTypeCount"));

        ktable.toStream().map((keyConcatenated, count) -> {

            String[] parts = keyConcatenated.split(",");
            String key = parts[0];
            String operationType = parts[1];
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("operationType", operationType);
            objectNode.put("operationCount", count);

            try {
                return new KeyValue<>(key, mapper.writeValueAsString(objectNode));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).to(OUTPUT_ITEM_OPERATIONS_AGGREGATION_TOPIC, Produced.with(Serdes.String(), Serdes.String()));
        Topology topology = builder.build();

        KafkaStreams kafkaStreams = new KafkaStreams(topology, streamConfig);
        kafkaStreams.start();

    }

}
