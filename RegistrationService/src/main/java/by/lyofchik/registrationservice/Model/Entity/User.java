package by.lyofchik.registrationservice.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@Table(name = "users", schema = "dbo")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "login", nullable = false, length = 50)
    private String login;

    @Column(name = "company_id", nullable = false)
    private Integer company;
    @Column(name = "surname", length = 50)
    private String surname;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "last_name", length = 50)
    private String lastName;
}