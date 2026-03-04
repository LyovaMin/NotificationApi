package by.lyofchik.webpushservice.Service;

import by.lyofchik.webpushservice.Model.DTO.NotificationRequest;
import by.lyofchik.webpushservice.Model.Entity.PushInfo;
import by.lyofchik.webpushservice.Model.Enum.Status;
import by.lyofchik.webpushservice.Model.Mapper.SubscriptionMapper;
import by.lyofchik.webpushservice.Repository.PushInfoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
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
    private final PushInfoRepository pushInfoRepository;

    @PostConstruct
    void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        this.pushService = new PushService(PUBLIC_KEY, PRIVATE_KEY, "mailto:lyovademyanov@gmail.com");
    }

    @Async("push_executor")
    public void sendPush(NotificationRequest request) {
        try {
            log.info("Start sending push - {}", Thread.currentThread().getName());
            Subscription subscription = subscriptionMapper.toWebPushSubscription(request.getSubscription());
            String payload = objectMapper.writeValueAsString(request.getPayload());
            Notification notification = Notification.builder()
                    .endpoint(subscription.endpoint)
                    .userPublicKey(subscription.keys.p256dh)
                    .userAuth(subscription.keys.auth)
                    .payload(payload)
                    .ttl(request.getTtl())
//                    .urgency(Urgency.HIGH)
                    .build();
            var response = pushService.send(notification);
            log.info("Push sending result: {}", response);

            PushInfo pushInfo = pushInfoRepository.getReferenceById(request.getPushId());
            if(response.getStatusLine().getStatusCode() < 300){
                pushInfo.setStatus(Status.SENT);
                pushInfoRepository.save(pushInfo);
                log.info("Push sent successfully={}", pushInfo);
            }else {
                pushInfo.setStatus(Status.SENDING_ERROR);
                pushInfoRepository.save(pushInfo);
                log.info("Push sent with error={}", pushInfo);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
