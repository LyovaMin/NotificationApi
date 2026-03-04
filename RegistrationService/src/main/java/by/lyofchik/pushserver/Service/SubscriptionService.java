package by.lyofchik.pushserver.Service;

import by.lyofchik.pushserver.Model.DTO.Request.DeleteAllSubsRequest;
import by.lyofchik.pushserver.Model.DTO.Response.Response;
import by.lyofchik.pushserver.Model.DTO.Request.SubscriptionAddRequest;
import by.lyofchik.pushserver.Model.DTO.Request.SubscriptionDeleteRequest;
import by.lyofchik.pushserver.Model.Entity.SubscriptionEntity;
import by.lyofchik.pushserver.Model.Entity.User;
import by.lyofchik.pushserver.Model.Mapper.SubscriptionMapper;
import by.lyofchik.pushserver.Repository.SubscriptionRepository;
import by.lyofchik.pushserver.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionService {
    SubscriptionMapper subscriptionMapper;
    UserRepository userRepository;
    SubscriptionRepository subscriptionRepository;

    public Response addSubscription(SubscriptionAddRequest request) {
        //adding to Redis
        User user = userRepository.findUserByLogin(request.getUserLogin());
        if (user == null) return Response.error();
        SubscriptionEntity subscription = subscriptionMapper.toSubscriptionEntity(request, user);
        subscriptionRepository.save(subscription);

        return Response.success();
    }

    public Response deleteSubscription(SubscriptionDeleteRequest request) {
        //delete from Redis
        subscriptionRepository.deleteByEndpoint(request.getSubscriptionEndpoint());
        return Response.success();
    }

    public Response deleteAllSubscriptions(DeleteAllSubsRequest request) {
        subscriptionRepository.deleteAllByUserLogin(request.getUserLogin());
        return Response.success();
    }
}
