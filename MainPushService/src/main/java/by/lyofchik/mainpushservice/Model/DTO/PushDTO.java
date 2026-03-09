package by.lyofchik.mainpushservice.Model.DTO;

import by.lyofchik.mainpushservice.Model.Enum.PushStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PushDTO{
    private UUID id;
    private String pushPayload;
    private String userLogin;
    private PushStatus status;
    private Integer batch;
}