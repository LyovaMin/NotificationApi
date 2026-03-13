package by.lyofchik.mainpushservice.Component;

import by.lyofchik.mainpushservice.Model.DTO.Response.Notification.NotiRs;
import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, NotiRs> kafkaTemplate;

    @Async("executor")
    public void sendNotificationToKafka(ChannelType channelType, NotiRs response){
        kafkaTemplate.send(
                channelType.getTopicName(),
                response.getSubscription().getEndpoint(),
                response
        );
        log.info("Notification send to kafka={}, channel={}", response, channelType);
    }
}
