package by.lyofchik.webpushservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushPayload{
    private String title;
    private String body;
    private String icon;
    private String url;
    private UUID pushId;
}