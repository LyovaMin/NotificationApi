package by.lyofchik.webpushservice.Model.Entity;

import by.lyofchik.webpushservice.Model.Enum.PushStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "push_info", schema = "dbo")
public class PushInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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