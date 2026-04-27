package by.lyofchik.webpushservice.Service;

import by.lyofchik.webpushservice.Component.StatusCollector;
import by.lyofchik.webpushservice.Exception.RetriableException;
import by.lyofchik.webpushservice.Model.DTO.NotiRq;
import by.lyofchik.webpushservice.Model.Enum.PushStatus;
import by.lyofchik.webpushservice.Service.Client.RegistrationServiceClient;
import by.lyofchik.webpushservice.Utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ErrorService {

    private final Set<String> RETRIABLE_ERRORS = Utils.getRetriableErrors();
    private final Set<String> SUBSCRIPTION_ERRORS = Utils.getSubscriptionErrors();

    private StatusCollector statusCollector;
    private RegistrationServiceClient registrationServiceClient;

    public void handle(NotiRq request, int code, String message) {
        UUID pushId = request.getPushId();

        if (RETRIABLE_ERRORS.contains(String.valueOf(code))) {
            log.warn("Retriable error for pushId {}: {} - {}. Setting status to SENDING_ERROR and triggering retry.", pushId, code, message);
            statusCollector.collect(pushId, PushStatus.SENDING_ERROR);
            throw new RetriableException("Retriable error from push service: " + code + " " + message);

        } else if (SUBSCRIPTION_ERRORS.contains(String.valueOf(code))) {
            log.warn("Subscription error for pushId {}: {} - {}. Unsubscribing and setting status to FAILED.", pushId, code, message);
            registrationServiceClient.unsubscribe(request.getSubscription());
            statusCollector.collect(pushId, PushStatus.FAILED);

        } else {
            log.warn("Unrecognized error for pushId {}: {} - {}. Setting status to FAILED.", pushId, code, message);
            statusCollector.collect(pushId, PushStatus.FAILED);
        }
    }

    public void handle(NotiRq request) {
        UUID pushId = request.getPushId();
        log.warn("Internal exception for pushId {}. Setting status to SENDING_ERROR and triggering retry.", pushId);
        statusCollector.collect(pushId, PushStatus.SENDING_ERROR);
        throw new RetriableException();
    }
}