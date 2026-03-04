package by.lyofchik.mainpushservice.Model.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionDto {
    private String endpoint;
    private String p256dh;
    private String auth;
}
