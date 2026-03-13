package by.lyofchik.mainpushservice.Component.Sender;

import by.lyofchik.mainpushservice.Feign.WebPushServiceApi;
import by.lyofchik.mainpushservice.Model.DTO.Response.Notification.NotiRs;
import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebPushSender implements ApiSender {
    private WebPushServiceApi webPushServiceApi;

    @Override
    public void send(NotiRs response) {
        webPushServiceApi.push(response);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.WEB;
    }
}
