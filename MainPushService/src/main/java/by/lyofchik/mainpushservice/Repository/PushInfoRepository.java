package by.lyofchik.mainpushservice.Repository;

import by.lyofchik.mainpushservice.Model.Entity.PushInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PushInfoRepository extends JpaRepository<PushInfo, Integer> {
    Optional<PushInfo> findById(UUID id);
    List<PushInfo> findPushInfoByBatch(int butchId);
}
