package by.lyofchik.webpushservice.Service;

import by.lyofchik.webpushservice.Model.DTO.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationService notificationService;

    @KafkaListener(
            topics = "notifications.webpush",
            containerFactory = "containerFactory")
    public void consumeNotification(NotificationRequest request){
        log.info("Get push request={}", request);
        notificationService.sendPush(request);
    }
}
