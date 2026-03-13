package by.lyofchik.mainpushservice.Component;

import by.lyofchik.mainpushservice.Component.Sender.ApiSender;
import by.lyofchik.mainpushservice.Model.DTO.Response.Notification.NotiRs;
import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ApiProducer {
    private final Map<ChannelType, ApiSender> senders;

    public ApiProducer(List<ApiSender> senders) {
        this.senders = senders.stream().collect(Collectors.toMap(ApiSender::getChannelType, sender -> sender));
    }

    public void sendPush(NotiRs response, ChannelType channelType) {
        ApiSender sender = senders.get(channelType);
        sender.send(response);
    }
}
