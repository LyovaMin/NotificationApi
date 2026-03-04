package by.lyofchik.pushserver.Model.DTO.Request;

import by.lyofchik.pushserver.Model.DTO.SubscriptionDto;
import by.lyofchik.pushserver.Model.Enum.ChannelType;
import lombok.Getter;

@Getter
public class SubscriptionAddRequest {
    private String userLogin;
    private ChannelType channelType;
    private SubscriptionDto subscription;
}
