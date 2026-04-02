package by.lyofchik.webpushservice.Repository;

import by.lyofchik.webpushservice.Model.Entity.PushInfo;
import by.lyofchik.webpushservice.Model.Enum.PushStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface PushInfoRepository extends JpaRepository<PushInfo, Integer> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE dbo.push_info SET status = :newStatus " +
            "WHERE id = :id " +
            "AND status <> 'DELIVERED' " +
            "AND status <> 'READ' " +
            "AND status <> 'DISMISSED'", nativeQuery = true)
    void updateStatusSecure(@Param("id") UUID id, @Param("newStatus") String newStatus);
}
