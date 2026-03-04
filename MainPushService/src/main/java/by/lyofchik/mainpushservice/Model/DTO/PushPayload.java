package by.lyofchik.mainpushservice.Model.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PushPayload{
    private String title;
    private String body;
    private String icon;
    private String url;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}