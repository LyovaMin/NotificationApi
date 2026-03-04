package by.lyofchik.webpushservice.Model.Entity;

import by.lyofchik.webpushservice.Model.Enum.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "push_info")
public class PushInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "push_payload", length = 512)
    private String payload;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_login", nullable = false)
    private User userLogin;

    @Column(name = "status", nullable = false, length = 15)
    private Status status;

    @Column(name = "batch_id", nullable = false)
    private Integer batchId;
}