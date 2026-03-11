package by.lyofchik.mainpushservice.Feign;

import by.lyofchik.mainpushservice.Configuration.FeignConfig;
import by.lyofchik.mainpushservice.Model.DTO.Response.Notification.NotiRs;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "WebPush", url = "localhost:9999", configuration = FeignConfig.class)
public interface WebPushServiceApi {
    @PostMapping("/api")
    void push(@RequestBody NotiRs response);
}
