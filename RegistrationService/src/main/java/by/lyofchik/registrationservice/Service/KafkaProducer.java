package by.lyofchik.registrationservice.Service;

import by.lyofchik.registrationservice.Model.DTO.Response.NotificationResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, NotificationResponse> kafkaTemplate;

    public void sendNotificationToKafka(NotificationResponse response){
        kafkaTemplate.send("notifications.webpush", response.getSubscription().getEndpoint(), response);
        log.info("Notification send to kafka={}", response);
    }
}
