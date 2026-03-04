package by.lyofchik.pushserver.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "dbo")
public class User {
    @Id
    @Column(name = "login", nullable = false, length = 50)
    private String login;

    @Column(name = "company_id", nullable = false)
    private Integer company;

}