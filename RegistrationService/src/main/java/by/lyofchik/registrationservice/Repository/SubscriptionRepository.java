package by.lyofchik.registrationservice.Repository;

import by.lyofchik.registrationservice.Model.Entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
    void deleteByEndpoint(String endpoint);

    void deleteAllByUserLogin(String userLogin);
}
