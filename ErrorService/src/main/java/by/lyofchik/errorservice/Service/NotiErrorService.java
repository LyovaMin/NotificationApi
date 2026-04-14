package by.lyofchik.errorservice.Service;

import by.lyofchik.errorservice.Kafka.KafkaProducer;
import by.lyofchik.errorservice.Model.DTO.ErrorRq;
import by.lyofchik.errorservice.Model.DTO.NotiRs;
import by.lyofchik.errorservice.Utils.Utils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotiErrorService {
    @Value("${topic.registration}")
    private String registrationTopic;
    private final Set<String> RETRIABLE_ERRORS = Utils.getRetriableErrors();
    private final Set<String> SUBSCRIPTION_ERRORS = Utils.getSubscriptionErrors();

    private final KafkaProducer kafkaProducer;

    public void handle(ErrorRq request) {
        String code = request.getErrorCode();
        String message = request.getErrorMessage();
        NotiRs response = new NotiRs(request.getEndpoint(), request.getPushId());

        if (RETRIABLE_ERRORS.contains(code)) {
            kafkaProducer.send(response, String.valueOf(request.getChannelType()));
        } else if (SUBSCRIPTION_ERRORS.contains(code)) {
            kafkaProducer.send(response, registrationTopic);
        } else {
            log.warn("Received unrecognized error code - {}, message - {}", code, message);
            return;
        }
        log.info("Received error code - {}, sent response - {}", code, response);
    }
}
