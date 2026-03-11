package by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo;

import by.lyofchik.mainpushservice.Model.Enum.PushStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateStatusRq {
    private PushStatus pushStatus;
    private UUID pushId;
}
