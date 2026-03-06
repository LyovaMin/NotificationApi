package by.lyofchik.mainpushservice.Model.Entity;

import by.lyofchik.mainpushservice.Model.Enum.BatchStatus;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private BatchStatus status;


}