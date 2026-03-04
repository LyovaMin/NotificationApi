package by.lyofchik.webpushservice.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "login", nullable = false, length = 50)
    private String login;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
}