package by.lyofchik.mainpushservice.Model.Mapper;

import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotiRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiListRq;
import by.lyofchik.mainpushservice.Model.Entity.PushInfo;
import by.lyofchik.mainpushservice.Model.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PushInfoMapper {

    @Mapping(target = "status", constant = "RECEIVED")
    @Mapping(target = "userLogin", source = "userLogin")
    @Mapping(target = "batch", source = "request.batchId")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "pushPayload", expression = "java(request.getPayload().toJson())")
    PushInfo toPushInfo(NotiRq request, String userLogin, UUID id);

    @Mapping(target = "status", constant = "RECEIVED")
    @Mapping(target = "userLogin", source = "userLogin")
    @Mapping(target = "batch", source = "request.batchId")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "pushPayload", expression = "java(request.getPayload().toJson())")
    PushInfo toPushInfo(NotiListRq request, String userLogin, UUID id);

    @Mapping(target = "status", constant = "RECEIVED")
    @Mapping(target = "userLogin", source = "userLogin")
    @Mapping(target = "batch", source = "request.batchId")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "pushPayload", expression = "java(request.getPayload().toJson())")
    PushInfo toPushInfo(AllNotiRq request, String userLogin, UUID id);
}
