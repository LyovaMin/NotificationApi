package by.lyofchik.registrationservice.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users", schema = "dbo")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "login", nullable = false, length = 50)
    private String login;

    @Column(name = "company_id", nullable = false)
    private Integer company;

}