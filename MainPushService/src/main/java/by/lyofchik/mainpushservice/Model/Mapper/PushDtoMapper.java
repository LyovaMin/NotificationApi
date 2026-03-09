package by.lyofchik.mainpushservice.Model.Mapper;

import by.lyofchik.mainpushservice.Model.DTO.PushDTO;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotificationsRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationsListRequest;
import by.lyofchik.mainpushservice.Model.Entity.PushInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PushDtoMapper {
    PushInfo toPushInfo(PushDTO pushDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userLogin", source = "userLogin")
    @Mapping(target = "batch", source = "request.batchId")
    @Mapping(target = "pushPayload", expression = "java(request.getPayload().toJson())")
    @Mapping(target = "status", constant = "SENT")
    PushDTO toPushDTO(AllNotificationsRequest request, String userLogin, UUID id);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userLogin", source = "userLogin")
    @Mapping(target = "batch", source = "request.batchId")
    @Mapping(target = "pushPayload", expression = "java(request.getPayload().toJson())")
    @Mapping(target = "status", constant = "SENT")
    PushDTO toPushDTO(NotificationsListRequest request, String userLogin, UUID id);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userLogin", source = "userLogin")
    @Mapping(target = "batch", source = "request.batchId")
    @Mapping(target = "pushPayload", expression = "java(request.getPayload().toJson())")
    @Mapping(target = "status", constant = "SENT")
    PushDTO toPushDTO(NotificationRequest request, String userLogin, UUID id);
}
