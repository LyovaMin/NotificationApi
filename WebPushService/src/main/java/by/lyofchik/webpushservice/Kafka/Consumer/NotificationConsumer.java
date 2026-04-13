package by.lyofchik.webpushservice.Kafka.Consumer;

import by.lyofchik.webpushservice.Model.DTO.NotificationRequest;
import by.lyofchik.webpushservice.Service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
@Slf4j
@AllArgsConstructor
public class NotificationConsumer {
    private NotificationService notificationService;

    @KafkaListener(topics = "${topic.webpush}",
            containerFactory = "containerFactory")
    public void consumeNotification(NotificationRequest request){
        log.info("Get push request={}", request);
        notificationService.sendPush(request);
    }
}
