package by.lyofchik.mainpushservice.Component;

import by.lyofchik.mainpushservice.Model.DTO.PushDTO;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotificationsRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationsListRequest;
import by.lyofchik.mainpushservice.Model.Entity.PushInfo;
import by.lyofchik.mainpushservice.Model.Mapper.PushDtoMapper;
import by.lyofchik.mainpushservice.Repository.PushInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@AllArgsConstructor
@Slf4j
public class PushInfoCollector {
    private PushDtoMapper pushDtoMapper;
    private PushInfoRepository pushInfoRepository;
    private final Queue<PushDTO> queue = new ConcurrentLinkedQueue<>();

    public void collect(AllNotificationsRequest request, String userLogin, UUID id) {
        PushDTO pushDTO = pushDtoMapper.toPushDTO(request, userLogin, id);
        queue.add(pushDTO);
    }

    public void collect(NotificationsListRequest request, String userLogin, UUID id) {
        PushDTO pushDTO = pushDtoMapper.toPushDTO(request, userLogin, id);
        queue.add(pushDTO);
    }

    public void collect(NotificationRequest request, String userLogin, UUID id) {
        PushDTO pushDTO = pushDtoMapper.toPushDTO(request, userLogin, id);
        queue.add(pushDTO);
    }

    @Scheduled(fixedDelay = 2000)
    public void sendToDB() {
        if (queue.isEmpty()) return;

        log.info("Sending push infos to DB - {}", queue.size());

        List<PushInfo> pushInfos = queue.stream()
                .map(pushDtoMapper::toPushInfo)
                .toList();

        pushInfoRepository.saveAll(pushInfos);
    }
}
