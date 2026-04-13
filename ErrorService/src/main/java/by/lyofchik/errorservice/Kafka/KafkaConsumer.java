package by.lyofchik.errorservice.Kafka;

import by.lyofchik.errorservice.Model.DTO.ErrorRq;
import by.lyofchik.errorservice.Service.NotiErrorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@EnableKafka
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {
    private NotiErrorService notiErrorService;

    @KafkaListener(topics = "${topic.error}",
            containerFactory = "containerFactory")
    public void consume(ErrorRq request) {
        log.info("Get error request - {}", request);
        notiErrorService.handle(request);
    }
}
