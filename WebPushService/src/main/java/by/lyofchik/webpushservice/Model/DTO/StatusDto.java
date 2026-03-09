package by.lyofchik.webpushservice.Model.DTO;

import by.lyofchik.webpushservice.Model.Enum.PushStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class StatusDto {
    private UUID pushId;
    private PushStatus status;
}
