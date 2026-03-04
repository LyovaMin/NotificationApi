package by.lyofchik.webpushservice.Repository;

import by.lyofchik.webpushservice.Model.Entity.SubscriptionEntity;
import by.lyofchik.webpushservice.Model.Enum.ChannelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
    List<SubscriptionEntity> findSubscriptionEntitiesByUserLoginAndChannelType(String login, ChannelType channelType);
}
