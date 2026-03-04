package by.lyofchik.mainpushservice.Model.DTO.Response.Notification;

import by.lyofchik.mainpushservice.Model.DTO.PushPayload;
import by.lyofchik.mainpushservice.Model.DTO.SubscriptionDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotificationResponse {
    SubscriptionDto subscription;
    PushPayload payload;
    private int ttl;
    private int pushId;
    private int batchId;
}
