package by.lyofchik.mainpushservice.Model.Entity;

import by.lyofchik.mainpushservice.Model.Enum.PushStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Table(name = "push_info", schema = "dbo")
public class PushInfo {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "push_payload", length = 512)
    private String pushPayload;

    @Column(name = "user_login", nullable = false)
    private String userLogin;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 15)
    private PushStatus status;

    @Column(name = "batch_id", nullable = false)
    private Integer batch;

}