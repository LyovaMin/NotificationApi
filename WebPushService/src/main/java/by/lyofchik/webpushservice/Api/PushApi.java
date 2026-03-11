package by.lyofchik.webpushservice.Api;

import by.lyofchik.webpushservice.Model.DTO.NotificationRequest;
import by.lyofchik.webpushservice.Service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PushApi {
    private NotificationService notificationService;

    @PostMapping
    public void push(@RequestBody NotificationRequest request){
        notificationService.sendPush(request);
    }
}
