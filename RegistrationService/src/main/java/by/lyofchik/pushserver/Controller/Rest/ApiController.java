package by.lyofchik.pushserver.Controller.Rest;

import by.lyofchik.pushserver.Model.DTO.Response.NotificationResponse;
import by.lyofchik.pushserver.Model.Mapper.SubMapper;
import by.lyofchik.pushserver.Service.KafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Subscription;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
public class ApiController {
    private final List<Subscription> subscriptions = new CopyOnWriteArrayList<>();
    private final SubMapper subMapper = new SubMapper();
    private KafkaProducer kafkaProducer;

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody Subscription subscription) {
        log.info("Subscription keys: {}", subscription.keys);
        subscriptions.add(subscription);
        log.info("Subscribed to {}", subscription.endpoint);
        int num = (int)(Math.random() * 100);
        NotificationResponse response = NotificationResponse.builder()
                .subscription(subMapper.toDTO(subscription))
                .payload(NotificationResponse.PushPayload.builder()
                        .title("Привет")
                        .body("Уведомление от Java " + num)
                        .icon("icon.png")
                        .url("/")
                        .build())
                .build();


        kafkaProducer.sendNotificationToKafka(response);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(@RequestBody Subscription subscription) {
        boolean removed = subscriptions.removeIf(s -> s.endpoint.equals(subscription.endpoint));

        if (removed) {
            log.info("Subscription deleted successfully: {}", subscription.endpoint);
        } else {
            log.warn("Endpoint not found: {}", subscription.endpoint);
        }
    }


}