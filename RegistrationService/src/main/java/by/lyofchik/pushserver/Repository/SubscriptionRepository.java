package by.lyofchik.pushserver.Repository;

import by.lyofchik.pushserver.Model.Entities.ChannelType;
import by.lyofchik.pushserver.Model.Entities.SubscriptionEntity;
import by.lyofchik.pushserver.Model.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
    List<SubscriptionEntity> findByUserAndChannelType(User user, ChannelType channelType);

    void deleteByEndpoint(String endpoint);

    void deleteAllByUserLogin(String userLogin);
}
