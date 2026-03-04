package by.lyofchik.mainpushservice.Model.DTO.Request.Notification;

import by.lyofchik.mainpushservice.Model.Enum.BatchStatus;
import by.lyofchik.mainpushservice.Model.Enum.NotificationPriority;
import by.lyofchik.mainpushservice.Model.DTO.PushPayload;
import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import lombok.Data;

@Data
public class NotificationRequest {
    private String userLogin;
    private int batchId;
    private int companyId;
    private PushPayload payload;
    private ChannelType channelType;
    private Integer ttl;
//    private NotificationPriority priority;
}
