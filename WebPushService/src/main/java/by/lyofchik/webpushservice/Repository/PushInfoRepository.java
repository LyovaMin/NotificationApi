package by.lyofchik.webpushservice.Repository;

import by.lyofchik.webpushservice.Model.Entity.PushInfo;
import by.lyofchik.webpushservice.Model.Enum.PushStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface PushInfoRepository extends JpaRepository<PushInfo, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE PushInfo p SET p.status = :status WHERE p.id IN :ids")
    void updateStatusForIds(@Param("status") PushStatus status, @Param("ids") List<UUID> ids);
}
