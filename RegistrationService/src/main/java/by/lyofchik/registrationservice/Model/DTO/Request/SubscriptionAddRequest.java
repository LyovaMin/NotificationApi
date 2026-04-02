package by.lyofchik.registrationservice.Model.DTO.Request;

import by.lyofchik.registrationservice.Model.DTO.SubscriptionDto;
import by.lyofchik.registrationservice.Model.Enum.ChannelType;
import lombok.Data;
import nl.martijndwars.webpush.Subscription;

@Data
public class SubscriptionAddRequest {
    private String userLogin;
    private ChannelType channelType;
    private Subscription subscription;
}
