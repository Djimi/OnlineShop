package manev.damyan.inventory.inventory.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import static manev.damyan.inventory.inventory.config.kafka.KafkaConfig.ITEMS_TOPIC;

@Service
@Slf4j
public class KafkaListeningService {

//    @KafkaListener(id = "myId1", clientIdPrefix = "myId1Name", topics = ITEMS_TOPIC, groupId = "firstTestGroup", concurrency = "1")
    public void listen(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Message received from Kafka in consumer 1 from firstTestGroup to partition: " + partition + " Message:" + message);
    }

    @KafkaListener(id = "myId2", clientIdPrefix = "myId2Name", topics = ITEMS_TOPIC, groupId = "firstTestGroup", concurrency = "1", containerFactory = "listenerContainerFactory")
    public void listen2(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Message received from Kafka in consumer 2 from firstTestGroup to partition: " + partition +
                " Message:" + message);
    }

//    @KafkaListener(id = "myIdAnother", topics = ITEMS_TOPIC, groupId = "anotherGroup")
    public void listenAnotherGroup(String message, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Message received from Kafka in consumer 1 from anotherGroup to partition: " + partition + " Message:" + message);
    }

}
