package by.lyofchik.registrationservice.Model.DTO.Request;

import lombok.Data;

@Data
public class SubscriptionDeleteRequest {
    private String userLogin;
    private String subscriptionEndpoint;
}
