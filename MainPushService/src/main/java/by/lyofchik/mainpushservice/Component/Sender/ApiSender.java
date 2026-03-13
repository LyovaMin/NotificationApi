package by.lyofchik.mainpushservice.Component.Sender;

import by.lyofchik.mainpushservice.Model.DTO.Response.Notification.NotiRs;
import by.lyofchik.mainpushservice.Model.Enum.ChannelType;

public interface ApiSender {
    void send(NotiRs response);
    ChannelType getChannelType();
}
