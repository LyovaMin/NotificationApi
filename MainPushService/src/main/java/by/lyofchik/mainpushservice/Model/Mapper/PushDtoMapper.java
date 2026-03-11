package by.lyofchik.mainpushservice.Model.Mapper;

import by.lyofchik.mainpushservice.Model.DTO.PushDTO;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotiRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiListRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiRq;
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
    @Mapping(target = "status", constant = "RECEIVED")
    PushDTO toPushDTO(AllNotiRq request, String userLogin, UUID id);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userLogin", source = "userLogin")
    @Mapping(target = "batch", source = "request.batchId")
    @Mapping(target = "pushPayload", expression = "java(request.getPayload().toJson())")
    @Mapping(target = "status", constant = "RECEIVED")
    PushDTO toPushDTO(NotiListRq request, String userLogin, UUID id);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userLogin", source = "userLogin")
    @Mapping(target = "batch", source = "request.batchId")
    @Mapping(target = "pushPayload", expression = "java(request.getPayload().toJson())")
    @Mapping(target = "status", constant = "RECEIVED")
    PushDTO toPushDTO(NotiRq request, String userLogin, UUID id);
}
