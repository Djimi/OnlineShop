package manev.damyan.inventory.inventory.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    public static final String ITEMS_TOPIC = "items-topic";
    public static final String ITEMS_ANALYTICS_ACTION_TOPIC = "items-analytics-action-topic";

    public static final String ITEMS_ANALYTICS_AGGREGATION_ACTION_TOPIC = "items-analytics-aggregation-action-topic";

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(ITEMS_TOPIC).partitions(10).replicas(1).build();
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "0");
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        DefaultKafkaProducerFactory<String, String> kafkaFactory = new DefaultKafkaProducerFactory<>(producerConfigs());
        return kafkaFactory;
    }

    @Bean
    public KafkaListenerContainerFactory listenerContainerFactory(ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory listenerFactory = new ConcurrentKafkaListenerContainerFactory();
        listenerFactory.setConsumerFactory(consumerFactory);
        listenerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.COUNT);
//        listenerFactory.setBatchListener(true);
        listenerFactory.getContainerProperties().setAckCount(3);
//        listenerFactory.getContainerProperties().setAckTime(10000);

        return listenerFactory;
    }

    @Bean
    public ConsumerFactory<String, String> demoConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
