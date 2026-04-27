package by.lyofchik.webpushservice.Service;

import by.lyofchik.webpushservice.Component.StatusCollector;
import by.lyofchik.webpushservice.Exception.RetriableException;
import by.lyofchik.webpushservice.Model.DTO.NotiRq;
import by.lyofchik.webpushservice.Model.DTO.PushPayload;
import by.lyofchik.webpushservice.Model.Entity.Batch;
import by.lyofchik.webpushservice.Model.Enum.BatchStatus;
import by.lyofchik.webpushservice.Model.Enum.PushStatus;
import by.lyofchik.webpushservice.Repository.BatchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

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
    private final BatchRepository batchRepository;
    private final StatusCollector statusCollector;
    private final ErrorService errorService;

    @PostConstruct
    void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(PUBLIC_KEY, PRIVATE_KEY, "mailto:lyovademyanov@gmail.com");
    }

    @Async("push_executor")
    public void sendPush(NotiRq request) {
        try {
            Batch batch = batchRepository.findById(request.getBatchId());
            if (Objects.nonNull(batch) && batch.getStatus() == BatchStatus.CANCELLED) {
                log.info("Batch {} is cancelled. Skipping pushId {}", request.getBatchId(), request.getPushId());
                statusCollector.collect(request.getPushId(), PushStatus.CANCELED);
                return;
            }

            log.info("Start sending push to {}", request.getPushId());
            PushPayload pushPayload = request.getPayload();
            pushPayload.setPushId(request.getPushId());
            String payload = objectMapper.writeValueAsString(pushPayload);

            Notification notification = Notification.builder()
                    .endpoint(request.getSubscription().getEndpoint())
                    .userPublicKey(request.getSubscription().getP256dh())
                    .userAuth(request.getSubscription().getAuth())
                    .payload(payload)
                    .ttl(request.getTtl())
                    .build();

            var response = pushService.send(notification);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode >= 200 && statusCode < 300) {
                statusCollector.collect(request.getPushId(), PushStatus.SENT);
                log.info("Push sent successfully to {}", request.getPushId());
            } else {
                errorService.handle(request, statusCode, response.getStatusLine().getReasonPhrase());
            }

        } catch (GeneralSecurityException | IOException | JoseException | ExecutionException | InterruptedException e) {
            log.error("Internal error while sending pushId {}: {}", request.getPushId(), e.getMessage());
            errorService.handle(request);
        }
    }

}