package by.lyofchik.webpushservice.Kafka;

import by.lyofchik.webpushservice.Exception.RetriableException;
import by.lyofchik.webpushservice.Model.DTO.NotiRq;
import by.lyofchik.webpushservice.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotiConsumer {
    private final NotificationService notificationService;

    @RetryableTopic(
            attempts = "4",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            autoCreateTopics = "false",
            include = RetriableException.class,
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE)
    @KafkaListener(topics = "${topic.webpush}",
            containerFactory = "containerFactory")
    public void consumeNotification(NotiRq request) {
        log.info("Received push request - {}", request.getPushId());
        notificationService.sendPush(request);
    }

    @DltHandler
    public void dltHandler(NotiRq request, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.warn("Message from topic {} with pushId {} is sent to DLT", topic, request.getPushId());
    }
}