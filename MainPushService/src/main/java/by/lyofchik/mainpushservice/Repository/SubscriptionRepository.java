package by.lyofchik.mainpushservice.Repository;

import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import by.lyofchik.mainpushservice.Model.Entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
    List<SubscriptionEntity> findSubscriptionEntitiesByUserLoginAndChannelType(String login, ChannelType channelType);

    Stream<SubscriptionEntity> findAllByUserLoginInAndChannelType(List<String> logins, ChannelType channelType);

    @Query("SELECT s FROM SubscriptionEntity s " +
            "JOIN User u ON s.userLogin = u.login " +
            "WHERE u.company = :companyId AND s.channelType = :channelType")
    Stream<SubscriptionEntity> findAllByCompanyAndChannel(
            @Param("companyId") int companyId,
            @Param("channelType") ChannelType channelType
    );
}
