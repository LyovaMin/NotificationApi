package by.lyofchik.mainpushservice.Service;

import by.lyofchik.mainpushservice.Model.DTO.Response.Notification.NotificationResponse;
import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, NotificationResponse> kafkaTemplate;

    @Async("executor")
    public void sendNotificationToKafka(ChannelType channelType, NotificationResponse response){
        kafkaTemplate.send(
                channelType.getTopicName(),
                response.getSubscription().getEndpoint(),
                response
        );
        log.info("Notification send to kafka={}, channel={}", response, channelType);
    }
}
