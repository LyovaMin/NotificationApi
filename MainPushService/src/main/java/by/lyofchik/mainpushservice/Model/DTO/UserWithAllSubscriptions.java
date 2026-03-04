package by.lyofchik.mainpushservice.Model.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserWithAllSubscriptions {
    private String login;

    private List<SubscriptionDto> subscriptions;
}
