package by.lyofchik.mainpushservice.Model.DTO.Response.PushInfo;

import by.lyofchik.mainpushservice.Model.Enum.PushStatus;
import lombok.Data;

@Data
public class PushInfoRs {
    private PushStatus pushStatus;
    private int pushId;
}
