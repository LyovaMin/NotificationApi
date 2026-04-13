package by.lyofchik.errorservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class NotiRs {
    private String endpoint;
    private UUID pushId;
}
