package by.lyofchik.mainpushservice.Model.DTO.Request.Notification;

import by.lyofchik.mainpushservice.Model.DTO.PushPayload;
import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import lombok.Data;

import java.util.List;

@Data
public class NotiListRq {
    private int batchId;
    private PushPayload payload;
    private ChannelType channelType;
    private List<String> usersLoginList;
    private int companyId;
    private int ttl;
    private Integer templateId;
    private List<String> data;
}
