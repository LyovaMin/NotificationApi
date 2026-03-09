package by.lyofchik.mainpushservice.Service;

import by.lyofchik.mainpushservice.Component.PushInfoCollector;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.CancelRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotificationsRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationsListRequest;
import by.lyofchik.mainpushservice.Model.DTO.Response.Response;
import by.lyofchik.mainpushservice.Model.DTO.Response.Notification.NotificationResponse;
import by.lyofchik.mainpushservice.Model.Entity.Batch;
import by.lyofchik.mainpushservice.Model.Entity.SubscriptionEntity;
import by.lyofchik.mainpushservice.Model.Entity.User;
import by.lyofchik.mainpushservice.Model.Enum.BatchStatus;
import by.lyofchik.mainpushservice.Model.Mapper.NotificationRequestMapper;
import by.lyofchik.mainpushservice.Model.Mapper.PushInfoMapper;
import by.lyofchik.mainpushservice.Repository.BatchRepository;
import by.lyofchik.mainpushservice.Repository.SubscriptionRepository;
import by.lyofchik.mainpushservice.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class SendingService {
    KafkaProducer kafkaProducer;
    UserRepository userRepository;
    SubscriptionRepository subscriptionRepository;
    NotificationRequestMapper notificationMapper;
    PushInfoMapper pushInfoMapper;
    BatchRepository batchRepository;
    TemplatesService templatesService;
    PushInfoCollector pushInfoCollector;

    //todo single push with api
    public Response sendPushToSingleUser(NotificationRequest request){
        log.info("sendPushToSingleUser - {}", request);
        if(request == null) {
            log.error("sendPushToSingleUser - request is null");
            return Response.error();
        }

        User user = userRepository.findUserByLogin(request.getUserLogin());
        if(user == null){
            log.error("sendPushToSingleUser - user not found");
            return Response.error();
        }

        List<SubscriptionEntity> subscriptions = subscriptionRepository
                .findSubscriptionEntitiesByUserLoginAndChannelType(user.getLogin(), request.getChannelType());
        if(subscriptions == null) {
            log.error("sendPushToSingleUser - subscriptions is null");
            return Response.error();
        }

        subscriptions.forEach(s -> {
            UUID uuid = UUID.randomUUID();
            pushInfoCollector.collect(request, user.getLogin(), uuid);

            NotificationResponse response = notificationMapper.toResponse(request, s, uuid);
            kafkaProducer.sendNotificationToKafka(request.getChannelType(), response);
        });

        return Response.success();
    }

    public Response sendListPushes(NotificationsListRequest request){
        log.info("Sending list pushes - {}", request);
        if(request == null) {
            log.error("sendListPushes - request is null");
            return Response.error();
        }

        Stream<User> users = userRepository.findUsersByCompany(request.getCompanyId());

        Batch batch = new Batch(request.getBatchId(), BatchStatus.OK);
        batchRepository.save(batch);

        users.forEach(user -> {
            List<SubscriptionEntity> subscriptions = subscriptionRepository
                    .findSubscriptionEntitiesByUserLoginAndChannelType(user.getLogin(), request.getChannelType());

            subscriptions.forEach(s -> {
                UUID uuid = UUID.randomUUID();
                pushInfoCollector.collect(request, user.getLogin(), uuid);

                NotificationResponse response = notificationMapper.toResponse(request, s, uuid);
                kafkaProducer.sendNotificationToKafka(request.getChannelType(), response);
            });
        });

        return Response.success();
    }

    public Response sendAllPushes(AllNotificationsRequest request){
        log.info("sendAllPushes - {}", request);
        if(request == null) {
            log.error("sendAllPushes - request is null");
            return Response.error();
        }

        Stream<User> users = userRepository.findUsersByCompany(request.getCompanyId());

        Batch batch = new Batch(request.getBatchId(), BatchStatus.OK);
        batchRepository.save(batch);

        users.forEach(user -> {
            List<SubscriptionEntity> subscriptions = subscriptionRepository
                    .findSubscriptionEntitiesByUserLoginAndChannelType(user.getLogin(), request.getChannelType());

            subscriptions.forEach(s -> {
                UUID uuid = UUID.randomUUID();
                pushInfoCollector.collect(request, user.getLogin(), uuid);

                NotificationResponse response = notificationMapper.toResponse(request, s, uuid);
                kafkaProducer.sendNotificationToKafka(request.getChannelType(), response);
            });
        });

        return Response.success();
    }

    public Response cancelPushes(CancelRequest request) {
        log.info("cancelPushes - {}", request);
        Batch batch = batchRepository.findById(request.getBatchId());
        if (batch == null) {
            log.error("cancelPushes - batch is null");
            return Response.error();
        }
        batch.setStatus(BatchStatus.CANCELLED);
        batchRepository.save(batch);

        return Response.success();
    }
}
