package by.lyofchik.webpushservice.Model.DTO;

import by.lyofchik.webpushservice.Model.Enum.ChannelType;
import lombok.Data;

import java.util.UUID;

@Data
public class ErrorRs {
    private SubscriptionDto subscription;
    private PushPayload payload;
    private int ttl;
    private UUID pushId;
    private int batchId;
    private int errorCode;
    private String errorMessage;
    private ChannelType channelType;
}
