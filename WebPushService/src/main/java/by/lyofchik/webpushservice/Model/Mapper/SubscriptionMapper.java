package by.lyofchik.webpushservice.Model.Mapper;

import by.lyofchik.webpushservice.Model.DTO.SubscriptionDto;
import nl.martijndwars.webpush.Subscription;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {
    public Subscription toWebPushSubscription(SubscriptionDto subscriptionDto) {
        return new Subscription(
                subscriptionDto.getEndpoint(),
                new Subscription.Keys(subscriptionDto.getP256dh(), subscriptionDto.getAuth())
        );
    }
}
