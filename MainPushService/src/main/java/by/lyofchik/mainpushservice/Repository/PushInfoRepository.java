package by.lyofchik.mainpushservice.Repository;

import by.lyofchik.mainpushservice.Model.Entity.PushInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PushInfoRepository extends JpaRepository<PushInfo, Integer> {
    PushInfo findById(int id);
    List<PushInfo> findPushInfoByBatch(int butchId);
}
