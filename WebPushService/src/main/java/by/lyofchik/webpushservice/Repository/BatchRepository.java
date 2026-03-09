package by.lyofchik.webpushservice.Repository;

import by.lyofchik.webpushservice.Model.Entity.Batch;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Integer> {
    @Cacheable(value = "batches", key = "#id",
            unless = "#result == null || #result.status != T(by.lyofchik.webpushservice.Model.Enum.BatchStatus).CANCELLED")
    Batch findById(int id);
}
