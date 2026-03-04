package by.lyofchik.pushserver.Repository;

import by.lyofchik.pushserver.Model.Entity.SubscriptionEntity;
import by.lyofchik.pushserver.Model.Entity.User;
import by.lyofchik.pushserver.Model.Enum.ChannelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
    void deleteByEndpoint(String endpoint);

    void deleteAllByUserLogin(String userLogin);
}
