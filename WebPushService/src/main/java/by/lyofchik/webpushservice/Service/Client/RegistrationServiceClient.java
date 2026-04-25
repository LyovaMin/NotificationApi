package by.lyofchik.webpushservice.Service.Client;

import by.lyofchik.webpushservice.Model.DTO.SubscriptionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "registration-service", url = "http://localhost:8888")
public interface RegistrationServiceClient {
    @PostMapping("/unsubscribe")
    void unsubscribe(SubscriptionDto subscription);
}