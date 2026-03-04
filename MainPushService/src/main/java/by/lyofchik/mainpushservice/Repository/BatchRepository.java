package by.lyofchik.mainpushservice.Repository;

import by.lyofchik.mainpushservice.Model.Entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Integer> {
    public Batch findById(int id);
}
