package by.lyofchik.errorservice.Service;

import by.lyofchik.errorservice.Kafka.KafkaProducer;
import by.lyofchik.errorservice.Model.DTO.ErrorRq;
import by.lyofchik.errorservice.Model.DTO.NotiRs;
import by.lyofchik.errorservice.Utils.Utils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotiErrorService {
    @Value("${topic.registration}")
    private String registrationTopic;
    @Value("${topic.main-service}")
    private String mainServiceTopic;

    private final KafkaProducer kafkaProducer;

    public void handle(ErrorRq request) {
        String code = request.getErrorCode();
        String message = request.getErrorMessage();
        NotiRs response = new NotiRs(request.getEndpoint(), request.getPushId());

        boolean isNumber = Utils.tryParse(code);
        if (isNumber) {

        } else {

        }
    }
}
