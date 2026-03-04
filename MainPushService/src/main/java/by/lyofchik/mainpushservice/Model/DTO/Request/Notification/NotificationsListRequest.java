package by.lyofchik.mainpushservice.Model.DTO.Request.Notification;

import by.lyofchik.mainpushservice.Model.DTO.PushPayload;
import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import lombok.Getter;

import java.util.List;

@Getter
public class NotificationsListRequest {
    private int batchId;
    private PushPayload payload;
    private ChannelType channelType;
    private List<String> usersLoginList;
    private int companyId;
    private int ttl;
}
