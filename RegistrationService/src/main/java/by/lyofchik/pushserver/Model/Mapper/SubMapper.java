package by.lyofchik.pushserver.Model.Mapper;

import by.lyofchik.pushserver.Model.DTO.SubscriptionDto;
import nl.martijndwars.webpush.Subscription;

public class SubMapper {
    public SubscriptionDto toDTO(Subscription subscription){
        return SubscriptionDto.builder()
                .endpoint(subscription.endpoint)
                .auth(subscription.keys.auth)
                .p256dh(subscription.keys.p256dh)
                .build();
    }
}
