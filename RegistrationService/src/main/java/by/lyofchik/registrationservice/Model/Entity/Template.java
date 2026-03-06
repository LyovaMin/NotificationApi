package by.lyofchik.registrationservice.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "templates", schema = "dbo")
public class Template {
    @Id
    @Column(name = "template_name", nullable = false, length = 50)
    private String templateName;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "body", length = 512)
    private String body;

    @Column(name = "company_id", nullable = false)
    private Integer company;


}