package by.lyofchik.mainpushservice.Model.DTO.Response.PushInfo;

import by.lyofchik.mainpushservice.Model.Enum.PushStatus;
import lombok.Data;

@Data
public class PushInfoResponse {
    private PushStatus pushStatus;
    private int pushId;
}
