package by.lyofchik.webpushservice.Model.Entity;

import by.lyofchik.webpushservice.Model.Enum.ChannelType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "subscriptions")
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "endpoint", nullable = false, length = 512)
    private String endpoint;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_login", nullable = false)
    private User userLogin;

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
    private boolean isActive;


}