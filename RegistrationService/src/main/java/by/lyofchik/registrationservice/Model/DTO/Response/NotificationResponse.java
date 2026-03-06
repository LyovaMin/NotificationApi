package by.lyofchik.registrationservice.Model.DTO.Response;

import by.lyofchik.registrationservice.Model.DTO.SubscriptionDto;
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

    @Builder
    @Data
    public static class PushPayload{
        private String title;
        private String body;
        private String icon;
        private String url;
    }
}
