package by.lyofchik.pushserver.Configuration;

import by.lyofchik.pushserver.Model.DTO.Response.NotificationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, NotificationResponse> producerFactory(ObjectMapper objectMapper){
        Map<String, Object> configProperties = new HashMap<>();
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        JsonSerializer<NotificationResponse> serializer = new JsonSerializer<>(objectMapper);

        return new DefaultKafkaProducerFactory<>(
                configProperties,
                new StringSerializer(),
                serializer
        );
    }

    @Bean
    public KafkaTemplate<String, NotificationResponse> kafkaTemplate(
            ProducerFactory<String, NotificationResponse> producerFactory
    ){
        return new KafkaTemplate<>(producerFactory);
    }
}
