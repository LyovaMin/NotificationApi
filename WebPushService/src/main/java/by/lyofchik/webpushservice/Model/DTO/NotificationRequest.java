package by.lyofchik.webpushservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private SubscriptionDto subscription;
    private PushPayload payload;
    private int ttl;
    private int pushId;
    private int batchId;
}
