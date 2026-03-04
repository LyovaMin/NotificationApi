package by.lyofchik.mainpushservice.Model.Mapper;

import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotificationsRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationsListRequest;
import by.lyofchik.mainpushservice.Model.DTO.Response.Notification.NotificationResponse;
import by.lyofchik.mainpushservice.Model.DTO.SubscriptionDto;
import by.lyofchik.mainpushservice.Model.Entity.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationRequestMapper {

    @Mapping(source = "request.payload", target = "payload")
    @Mapping(source = "request.ttl", target = "ttl")
    @Mapping(source = "subscription", target = "subscription")
    NotificationResponse toResponse(NotificationRequest request, SubscriptionEntity subscription, int pushId);

    @Mapping(source = "request.payload", target = "payload")
    @Mapping(source = "request.ttl", target = "ttl")
    @Mapping(source = "subscription", target = "subscription")
    NotificationResponse toResponse(NotificationsListRequest request, SubscriptionEntity subscription, int pushId);

    @Mapping(source = "request.payload", target = "payload")
    @Mapping(source = "request.ttl", target = "ttl")
    @Mapping(source = "subscription", target = "subscription")
    NotificationResponse toResponse(AllNotificationsRequest request, SubscriptionEntity subscription, int pushId);

    @Mapping(source = "entity.authKey", target = "auth")
    SubscriptionDto mapToSubscriptionDto(SubscriptionEntity entity);
}
