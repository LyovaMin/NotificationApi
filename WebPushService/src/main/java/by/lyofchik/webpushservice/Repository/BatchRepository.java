package by.lyofchik.webpushservice.Repository;

import by.lyofchik.webpushservice.Model.Entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Integer> {
    public Batch findById(int id);
}
