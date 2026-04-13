package by.lyofchik.errorservice.Kafka;

import by.lyofchik.errorservice.Model.DTO.NotiRs;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaProducer {
    private KafkaTemplate<String, NotiRs> kafkaTemplate;

    @Async
    public void send(NotiRs response, String topic) {
        kafkaTemplate.send(topic, response.getEndpoint(), response);
        log.info("Notification send to Kafka - {}", response);
    }
}
