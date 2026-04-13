package by.lyofchik.errorservice.Model.DTO;

import by.lyofchik.errorservice.Model.Enum.ChannelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorRq {
    private String endpoint;
    private UUID pushId;
    private String errorCode;
    private String errorMessage;
    private ChannelType channelType;
}
