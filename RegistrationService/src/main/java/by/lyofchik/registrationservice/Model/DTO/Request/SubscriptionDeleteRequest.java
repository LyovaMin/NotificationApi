package by.lyofchik.registrationservice.Model.DTO.Request;

import lombok.Getter;

@Getter
public class SubscriptionDeleteRequest {
    private String userLogin;
    private String subscriptionEndpoint;
}
