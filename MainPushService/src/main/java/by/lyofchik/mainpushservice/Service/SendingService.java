package by.lyofchik.mainpushservice.Service;

import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.CancelRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotificationsRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationsListRequest;
import by.lyofchik.mainpushservice.Model.DTO.Response.Response;
import by.lyofchik.mainpushservice.Model.DTO.Response.Notification.NotificationResponse;
import by.lyofchik.mainpushservice.Model.Entity.Batch;
import by.lyofchik.mainpushservice.Model.Entity.PushInfo;
import by.lyofchik.mainpushservice.Model.Entity.SubscriptionEntity;
import by.lyofchik.mainpushservice.Model.Entity.User;
import by.lyofchik.mainpushservice.Model.Enum.BatchStatus;
import by.lyofchik.mainpushservice.Model.Mapper.NotificationRequestMapper;
import by.lyofchik.mainpushservice.Model.Mapper.PushInfoMapper;
import by.lyofchik.mainpushservice.Repository.BatchRepository;
import by.lyofchik.mainpushservice.Repository.PushInfoRepository;
import by.lyofchik.mainpushservice.Repository.SubscriptionRepository;
import by.lyofchik.mainpushservice.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SendingService {
    KafkaProducer kafkaProducer;
    UserRepository userRepository;
    PushInfoRepository pushInfoRepository;
    SubscriptionRepository subscriptionRepository;
    NotificationRequestMapper notificationMapper;
    PushInfoMapper pushInfoMapper;
    BatchRepository batchRepository;

    public Response sendPushToSingleUser(NotificationRequest request){
        if(request == null) return Response.error();

        User user = userRepository.findUserByLogin(request.getUserLogin());
        if(user == null) return Response.error();

        List<SubscriptionEntity> subscriptions = subscriptionRepository
                .findSubscriptionEntitiesByUserLoginAndChannelType(user.getLogin(), request.getChannelType());
        if(subscriptions == null) return Response.error();

        PushInfo pushInfo = pushInfoMapper.toPushInfo(request);
        pushInfoRepository.save(pushInfo);

        subscriptions.forEach(s -> {
            NotificationResponse response = notificationMapper.toResponse(request, s, pushInfo.getId());
            kafkaProducer.sendNotificationToKafka(request.getChannelType(), response);
        });

        Batch batch = new Batch(request.getBatchId(), BatchStatus.OK);
        batchRepository.save(batch);

        return Response.success(pushInfo);
    }

    public Response sendListPushes(NotificationsListRequest request){
        if(request == null) return Response.error();

        List<User> users = request.getUsersLoginList()
                .stream()
                .map(userRepository::findUserByLogin)
                .toList();
        if (users.isEmpty()) return Response.error();

        List<PushInfo> pushInfos = new ArrayList<>();

        users.forEach(user -> {
            List<SubscriptionEntity> subscriptions = subscriptionRepository
                    .findSubscriptionEntitiesByUserLoginAndChannelType(user.getLogin(), request.getChannelType());
            PushInfo pushInfo = pushInfoMapper.toPushInfo(request, user);
            pushInfoRepository.save(pushInfo);
            pushInfos.add(pushInfo);

            subscriptions.forEach(s -> {
                NotificationResponse response = notificationMapper.toResponse(request, s, pushInfo.getId());
                kafkaProducer.sendNotificationToKafka(request.getChannelType(), response);
            });
        });
        Batch batch = new Batch(request.getBatchId(), BatchStatus.OK);
        batchRepository.save(batch);

        return Response.success(pushInfos);
    }

    public Response sendAllPushes(AllNotificationsRequest request){
        if(request == null) return Response.error();

        List<User> users = userRepository.findUsersByCompany(request.getCompanyId());
        if (users.isEmpty()) return Response.error();

        List<PushInfo> pushInfos = new ArrayList<>();

        users.forEach(user -> {
            List<SubscriptionEntity> subscriptions = subscriptionRepository
                    .findSubscriptionEntitiesByUserLoginAndChannelType(user.getLogin(), request.getChannelType());
            PushInfo pushInfo = pushInfoMapper.toPushInfo(request, user);
            pushInfoRepository.save(pushInfo);
            pushInfos.add(pushInfo);

            subscriptions.forEach(s -> {
                NotificationResponse response = notificationMapper.toResponse(request, s, pushInfo.getId());
                kafkaProducer.sendNotificationToKafka(request.getChannelType(), response);
            });
        });
        Batch batch = new Batch(request.getBatchId(), BatchStatus.OK);
        batchRepository.save(batch);

        return Response.success(pushInfos);
    }

    public Response cancelPushes(CancelRequest request) {
        Batch batch = batchRepository.findById(request.getBatchId());
        if (batch == null) return Response.error();
        batch.setStatus(BatchStatus.CANCELLED);
        batchRepository.save(batch);

        return Response.success();
    }
}
