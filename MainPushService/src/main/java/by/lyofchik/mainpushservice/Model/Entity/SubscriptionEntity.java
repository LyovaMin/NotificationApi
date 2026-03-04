package by.lyofchik.mainpushservice.Model.Entity;

import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "subscriptions", schema = "dbo")
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "endpoint", nullable = false, length = 512)
    private String endpoint;

    @Column(name = "user_login", nullable = false, length = 50)
    private String userLogin;

    @Column(name = "auth_key")
    private String authKey;

    @Column(name = "p256dh")
    private String p256dh;

    @Column(name = "last_seen_at")
    private LocalDate lastSeenAt;

    @Column(name = "channel_type", length = 15)
    private ChannelType channelType;

    @ColumnDefault("1")
    @Column(name = "is_active", columnDefinition = "tinyint")
    private Boolean isActive;

}