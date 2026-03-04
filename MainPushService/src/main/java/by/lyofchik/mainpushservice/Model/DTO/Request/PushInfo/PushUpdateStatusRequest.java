package by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo;

import by.lyofchik.mainpushservice.Model.Enum.PushStatus;
import lombok.Getter;

@Getter
public class PushUpdateStatusRequest {
    private PushStatus pushStatus;
    private int pushId;
}
