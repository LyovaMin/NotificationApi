package by.lyofchik.mainpushservice.Service;

import by.lyofchik.mainpushservice.Component.ApiProducer;
import by.lyofchik.mainpushservice.Kafka.KafkaProducer;
import by.lyofchik.mainpushservice.Component.PushInfoCollector;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotiRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiListRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiRq;
import by.lyofchik.mainpushservice.Model.DTO.Response.Notification.NotiRs;
import by.lyofchik.mainpushservice.Model.DTO.Response.Response;
import by.lyofchik.mainpushservice.Model.Entity.Batch;
import by.lyofchik.mainpushservice.Model.Entity.SubscriptionEntity;
import by.lyofchik.mainpushservice.Model.Entity.User;
import by.lyofchik.mainpushservice.Model.Enum.BatchStatus;
import by.lyofchik.mainpushservice.Model.Mapper.NotiRqMapper;
import by.lyofchik.mainpushservice.Repository.BatchRepository;
import by.lyofchik.mainpushservice.Repository.SubscriptionRepository;
import by.lyofchik.mainpushservice.Repository.TemplateRepository;
import by.lyofchik.mainpushservice.Repository.UserRepository;
import by.lyofchik.mainpushservice.Utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.ap.shaded.freemarker.template.Template;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class SendingService {
    KafkaProducer kafkaProducer;
    UserRepository userRepository;
    SubscriptionRepository subscriptionRepository;
    NotiRqMapper notificationMapper;
    BatchRepository batchRepository;
    PushInfoCollector pushInfoCollector;
    ApiProducer apiProducer;

    public Response sendPushToSingleUser(NotiRq request){
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

        if(request.getTemplateId() != null){
            Utils.fillMapFromUser(user, request.getData());
            String filledBody = Utils.fillTemplate(request.getTemplateId(), request.getData());
            if(filledBody == null){
                return Response.error();
            }
            request.getPayload().setBody(filledBody);
        }

        List<SubscriptionEntity> subscriptions = subscriptionRepository
                .findSubscriptionEntitiesByUserLoginAndChannelType(user.getLogin(), request.getChannelType());
        if(subscriptions == null) {
            log.error("sendPushToSingleUser - subscriptions is null");
            return Response.error();
        }

        List<UUID> uuids = new ArrayList<>();

        subscriptions.forEach(s -> {
            UUID uuid = UUID.randomUUID();
            uuids.add(uuid);
            pushInfoCollector.collect(request, user.getLogin(), uuid);

            NotiRs response = notificationMapper.toResponse(request, s, uuid);
            apiProducer.sendPush(response, request.getChannelType());
            //kafkaProducer.sendNotificationToKafka(request.getChannelType(), response);
        });

        return Response.success(uuids);
    }

    @Transactional
    public Response sendListPushes(NotiListRq request){
        log.info("Sending list pushes - {}", request);
        if(request == null) {
            log.error("sendListPushes - request is null");
            return Response.error();
        }

        Stream<SubscriptionEntity> subscriptions = subscriptionRepository
                .findAllByUserLoginInAndChannelType(request.getUsersLoginList(), request.getChannelType());

        Batch batch = new Batch(request.getBatchId(), BatchStatus.OK);
        batchRepository.saveAndFlush(batch);

        subscriptions.forEach(subscription -> {
            UUID uuid = UUID.randomUUID();
            pushInfoCollector.collect(request, subscription.getUserLogin(), uuid);
            NotiRs response = notificationMapper.toResponse(request, subscription, uuid);
            kafkaProducer.sendNotificationToKafka(request.getChannelType(), response);
        });

        return Response.success();
    }

    @Transactional
    public Response sendAllPushes(AllNotiRq request){
        log.info("sendAllPushes - {}", request);
        if(request == null) {
            log.error("sendAllPushes - request is null");
            return Response.error();
        }

        Batch batch = new Batch(request.getBatchId(), BatchStatus.OK);
        batchRepository.saveAndFlush(batch);

        Stream<SubscriptionEntity> subscriptions = subscriptionRepository.findAllByCompanyAndChannel(
                request.getCompanyId(), request.getChannelType());

        Map<String, List<UUID>> map = new HashMap<>();

        subscriptions.forEach(subscription -> {
            UUID uuid = UUID.randomUUID();
            pushInfoCollector.collect(request, subscription.getUserLogin(), uuid);
            NotiRs response = notificationMapper.toResponse(request, subscription, uuid);
            if(map.containsKey(subscription.getUserLogin())) {
                map.get(subscription.getUserLogin()).add(uuid);
            } else{
                map.put(subscription.getUserLogin(), new ArrayList<>());
                map.get(subscription.getUserLogin()).add(uuid);
            }
            kafkaProducer.sendNotificationToKafka(request.getChannelType(), response);
        });

        return Response.success(map);
    }
}
