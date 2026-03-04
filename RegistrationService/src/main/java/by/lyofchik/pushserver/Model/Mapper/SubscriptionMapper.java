package by.lyofchik.pushserver.Model.Mapper;

import by.lyofchik.pushserver.Model.DTO.Request.SubscriptionAddRequest;
import by.lyofchik.pushserver.Model.Entity.SubscriptionEntity;
import by.lyofchik.pushserver.Model.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    @Mapping(source = "request.subscription.endpoint", target = "endpoint")
    @Mapping(source = "request.subscription.p256dh", target = "p256dh")
    @Mapping(source = "request.subscription.auth", target = "authKey")
    @Mapping(source = "request.channelType", target = "channelType")
    @Mapping(target = "isActive", constant = "true")
    @Mapping(target = "lastSeenAt", expression = "java(java.time.LocalDate.now())")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(target = "id", ignore = true)
    SubscriptionEntity toSubscriptionEntity(SubscriptionAddRequest request, User user);
}
