package by.lyofchik.registrationservice.Service;

import by.lyofchik.registrationservice.Model.DTO.Request.DeleteAllSubsRequest;
import by.lyofchik.registrationservice.Model.DTO.Response.Response;
import by.lyofchik.registrationservice.Model.DTO.Request.SubscriptionAddRequest;
import by.lyofchik.registrationservice.Model.DTO.Request.SubscriptionDeleteRequest;
import by.lyofchik.registrationservice.Model.Entity.SubscriptionEntity;
import by.lyofchik.registrationservice.Model.Entity.User;
import by.lyofchik.registrationservice.Model.Mapper.SubscriptionMapper;
import by.lyofchik.registrationservice.Repository.SubscriptionRepository;
import by.lyofchik.registrationservice.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SubscriptionService {
    SubscriptionMapper subscriptionMapper;
    UserRepository userRepository;
    SubscriptionRepository subscriptionRepository;

    public Response addSubscription(SubscriptionAddRequest request) {
        log.info("addSubscription - {}", request.getUserLogin());
        //adding to Redis
        User user = userRepository.findUserByLogin(request.getUserLogin());
        if (user == null) return Response.error();
        SubscriptionEntity subscription = subscriptionMapper.toSubscriptionEntity(request, user);
        subscriptionRepository.save(subscription);

        return Response.success();
    }

    public Response deleteSubscription(SubscriptionDeleteRequest request) {
        log.info("deleteSubscription - {}", request);
        //delete from Redis
        subscriptionRepository.deleteByEndpoint(request.getSubscriptionEndpoint());
        return Response.success();
    }

    public Response deleteAllSubscriptions(DeleteAllSubsRequest request) {
        log.info("deleteAllSubscriptions - {}", request);
        subscriptionRepository.deleteAllByUserLogin(request.getUserLogin());
        return Response.success();
    }
}
