package by.lyofchik.webpushservice.Api;

import by.lyofchik.webpushservice.Exception.RetriableException;
import by.lyofchik.webpushservice.Model.DTO.NotiRq;
import by.lyofchik.webpushservice.Service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PushApi {
    private NotificationService notificationService;

    @PostMapping
    public void push(@RequestBody NotiRq request) {
        try{
            notificationService.sendPush(request);
        } catch (RetriableException e) {
            log.error(e.getMessage());
        }
    }
}
