package by.lyofchik.mainpushservice.Repository;

import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import by.lyofchik.mainpushservice.Model.Entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
    List<SubscriptionEntity> findSubscriptionEntitiesByUserLoginAndChannelType(String login, ChannelType channelType);
}
