package by.lyofchik.mainpushservice.Model.DTO.Request.Notification;

import by.lyofchik.mainpushservice.Model.DTO.PushPayload;
import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import lombok.Data;

@Data
public class NotiRq {
    private String userLogin;
    private int companyId;
    private PushPayload payload;
    private ChannelType channelType;
    private Integer ttl;
//    private NotificationPriority priority;
}
