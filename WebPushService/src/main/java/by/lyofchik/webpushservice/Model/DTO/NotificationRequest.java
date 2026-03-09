package by.lyofchik.webpushservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private SubscriptionDto subscription;
    private PushPayload payload;
    private int ttl;
    private UUID pushId;
    private int batchId;
}
