package by.lyofchik.webpushservice.Component;

import by.lyofchik.webpushservice.Model.DTO.StatusDto;
import by.lyofchik.webpushservice.Model.Entity.PushInfo;
import by.lyofchik.webpushservice.Model.Enum.PushStatus;
import by.lyofchik.webpushservice.Repository.PushInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
@Slf4j
@AllArgsConstructor
public class StatusCollector {
    private PushInfoRepository pushInfoRepository;
    private final Queue<StatusDto> queue = new ConcurrentLinkedQueue<>();

    public void collect(UUID pushId, PushStatus status) {
        StatusDto statusDto = new StatusDto(pushId, status);
        queue.add(statusDto);
    }

    @Scheduled(fixedDelay = 2000)
    private void sendToDB() {
        if (queue.isEmpty()) return;

        List<PushInfo> pushInfos = new ArrayList<>();
        while (!queue.isEmpty()) {
            StatusDto statusDto = queue.poll();
            PushInfo pushInfo = pushInfoRepository.findById(statusDto.getPushId());
            pushInfo.setStatus(statusDto.getStatus());
            pushInfos.add(pushInfo);
        }

        pushInfoRepository.saveAll(pushInfos);
    }
}