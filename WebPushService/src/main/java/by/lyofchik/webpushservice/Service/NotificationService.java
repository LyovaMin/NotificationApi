package by.lyofchik.webpushservice.Service;

import by.lyofchik.webpushservice.Component.StatusCollector;
import by.lyofchik.webpushservice.Model.DTO.NotificationRequest;
import by.lyofchik.webpushservice.Model.Entity.Batch;
import by.lyofchik.webpushservice.Model.Enum.BatchStatus;
import by.lyofchik.webpushservice.Model.Enum.PushStatus;
import by.lyofchik.webpushservice.Model.Mapper.SubscriptionMapper;
import by.lyofchik.webpushservice.Repository.BatchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.security.Security;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    @Value("${vapid.public.key}")
    private String PUBLIC_KEY;
    @Value("${vapid.private.key}")
    private String PRIVATE_KEY;
    private PushService pushService;
    private final ObjectMapper objectMapper;
    private final SubscriptionMapper subscriptionMapper;
    private final BatchRepository batchRepository;
    private final StatusCollector statusCollector;

    @PostConstruct
    void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        this.pushService = new PushService(PUBLIC_KEY, PRIVATE_KEY, "mailto:lyovademyanov@gmail.com");
    }

    @Async("push_executor")
    public void sendPush(NotificationRequest request) {
        try {
            Batch batch = batchRepository.findById(request.getBatchId());
            if (batch == null) {
                log.info("Batch not found");
                return;
            }
            if (batch.getStatus() == BatchStatus.CANCELLED){
                log.info("Cancelling push request - {}", request);
                statusCollector.collect(request.getPushId(), PushStatus.CANCELED);
                return;
            }

            log.info("Start sending push - {}", Thread.currentThread().getName());
            String payload = objectMapper.writeValueAsString(request.getPayload());
            Notification notification = Notification.builder()
                    .endpoint(request.getSubscription().getEndpoint())
                    .userPublicKey(request.getSubscription().getP256dh())
                    .userAuth(request.getSubscription().getAuth())
                    .payload(payload)
                    .ttl(request.getTtl())
//                    .urgency(Urgency.HIGH)
                    .build();
            var response = pushService.send(notification);
            log.info("Push sending result - {}", response);

            if(response.getStatusLine().getStatusCode() < 300){
                statusCollector.collect(request.getPushId(), PushStatus.SENT);
                log.info("Push sent successfully - {}", request);
            }else {
                statusCollector.collect(request.getPushId(), PushStatus.SENDING_ERROR);
                log.info("Push sent with error - {}", request);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
