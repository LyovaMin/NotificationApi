package by.lyofchik.webpushservice.Model.Entity;

import by.lyofchik.webpushservice.Model.Enum.BatchStatus;
import jakarta.persistence.*;
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