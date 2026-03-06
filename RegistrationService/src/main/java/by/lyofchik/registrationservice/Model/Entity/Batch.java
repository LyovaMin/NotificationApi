package by.lyofchik.registrationservice.Model.Entity;

import by.lyofchik.registrationservice.Model.Enum.BatchStatus;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "batches", schema = "dbo")
public class Batch {
    @Id
    @Column(name = "batch_id", nullable = false)
    private Integer id;

    @Column(name = "status", length = 10)
    private BatchStatus status;


}