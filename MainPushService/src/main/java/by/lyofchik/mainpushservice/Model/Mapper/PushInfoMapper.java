package by.lyofchik.mainpushservice.Model.Mapper;

import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotificationsRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationsListRequest;
import by.lyofchik.mainpushservice.Model.Entity.PushInfo;
import by.lyofchik.mainpushservice.Model.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PushInfoMapper {
    @Mapping(target = "status", constant = "RECEIVED")
    @Mapping(target = "userLogin", source = "request.userLogin")
    @Mapping(target = "batch", source = "request.batchId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pushPayload", expression = "java(request.getPayload().toJson())")
    PushInfo toPushInfo(NotificationRequest request);

    @Mapping(target = "status", constant = "RECEIVED")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(target = "batch", source = "request.batchId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pushPayload", expression = "java(request.getPayload().toJson())")
    PushInfo toPushInfo(NotificationsListRequest request, User user);

    @Mapping(target = "status", constant = "RECEIVED")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(target = "batch", source = "request.batchId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pushPayload", expression = "java(request.getPayload().toJson())")
    PushInfo toPushInfo(AllNotificationsRequest request, User user);
}
