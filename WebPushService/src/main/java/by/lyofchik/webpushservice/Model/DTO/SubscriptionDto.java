package by.lyofchik.webpushservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDto {
    private String endpoint;
    private String p256dh;
    private String auth;
}