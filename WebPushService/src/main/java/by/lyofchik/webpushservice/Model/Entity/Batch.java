package by.lyofchik.webpushservice.Model.Entity;

import by.lyofchik.webpushservice.Model.Enum.BatchStatus;
import by.lyofchik.webpushservice.Model.Enum.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "batches")
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id", nullable = false)
    private Integer id;

    @Column(name = "Status", nullable = false, length = 15)
    private BatchStatus status;

}