package by.lyofchik.pushserver.Model.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_login", nullable = false)
    private User userLogin;

    @Column(name = "auth_key", length = 255)
    private String authKey;

    @Column(name = "p256dh", length = 255)
    private String p256dh;

    @Column(name = "last_seen_at")
    private LocalDate lastSeenAt;

    @Column(name = "channel_type", length = 15)
    private ChannelType channelType;

    @Column(name = "is_active", columnDefinition = "tinyint")
    private boolean isActive;
}