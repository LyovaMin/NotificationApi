package by.lyofchik.webpushservice.Repository;

import by.lyofchik.webpushservice.Model.Entity.PushInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushInfoRepository extends JpaRepository<PushInfo, Integer> {
    PushInfo findById(int id);
    List<PushInfo> findPushInfoByBatchId(int butchId);
}
