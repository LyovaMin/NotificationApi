package by.lyofchik.webpushservice.Configuration;

import by.lyofchik.webpushservice.Model.DTO.NotificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.*;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, NotificationRequest> consumerFactory(ObjectMapper objectMapper){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "warehouse-group-v3");

        JsonDeserializer<NotificationRequest> deserializer = new JsonDeserializer<>(NotificationRequest.class, objectMapper);
        deserializer.setUseTypeHeaders(false);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                properties,
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(deserializer)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationRequest> containerFactory(
            ConsumerFactory<String, NotificationRequest> consumerFactory
    ){
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, NotificationRequest>();
        containerFactory.setConcurrency(5);
        containerFactory.setConsumerFactory(consumerFactory);
        return containerFactory;
    }

}
