package by.lyofchik.mainpushservice.Model.Mapper;

import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotiRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiListRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiRq;
import by.lyofchik.mainpushservice.Model.DTO.Response.Notification.NotiRs;
import by.lyofchik.mainpushservice.Model.DTO.SubscriptionDto;
import by.lyofchik.mainpushservice.Model.Entity.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface NotiRqMapper {

    @Mapping(source = "request.payload", target = "payload")
    @Mapping(source = "request.ttl", target = "ttl")
    @Mapping(source = "subscription", target = "subscription")
    NotiRs toResponse(NotiRq request, SubscriptionEntity subscription, UUID pushId);

    @Mapping(source = "request.payload", target = "payload")
    @Mapping(source = "request.ttl", target = "ttl")
    @Mapping(source = "subscription", target = "subscription")
    NotiRs toResponse(NotiListRq request, SubscriptionEntity subscription, UUID pushId);

    @Mapping(source = "request.payload", target = "payload")
    @Mapping(source = "request.ttl", target = "ttl")
    @Mapping(source = "subscription", target = "subscription")
    NotiRs toResponse(AllNotiRq request, SubscriptionEntity subscription, UUID pushId);

    @Mapping(source = "entity.authKey", target = "auth")
    SubscriptionDto mapToSubscriptionDto(SubscriptionEntity entity);
}
