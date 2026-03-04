package by.lyofchik.mainpushservice.Model.DTO.Request.Notification;

import by.lyofchik.mainpushservice.Model.DTO.PushPayload;
import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import lombok.Getter;

@Getter
public class AllNotificationsRequest {
    private int batchId;
    private int companyId;
    private ChannelType channelType;
    private PushPayload payload;
    private int ttl;
}
