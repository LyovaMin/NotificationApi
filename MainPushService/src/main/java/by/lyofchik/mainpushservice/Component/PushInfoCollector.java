package by.lyofchik.mainpushservice.Component;

import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotiRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiListRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiRq;
import by.lyofchik.mainpushservice.Model.Entity.PushInfo;
import by.lyofchik.mainpushservice.Model.Mapper.PushInfoMapper;
import by.lyofchik.mainpushservice.Repository.PushInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@AllArgsConstructor
@Slf4j
public class PushInfoCollector {
    private PushInfoMapper pushInfoMapper;
    private PushInfoRepository pushInfoRepository;
    private final Queue<PushInfo> queue = new ConcurrentLinkedQueue<>();
    private static final int BATCH_SIZE = 1000;
    private static final int MAX_BATCHES = 10;

    public void collect(AllNotiRq request, String userLogin, UUID id) {
        log.info("Collecting push - {}", id);
        queue.add(pushInfoMapper.toPushInfo(request, userLogin, id));
    }

    public void collect(NotiListRq request, String userLogin, UUID id) {
        log.info("Collecting push - {}", id);
        queue.add(pushInfoMapper.toPushInfo(request, userLogin, id));
    }

    public void collect(NotiRq request, String userLogin, UUID id) {
        log.info("Collecting push - {}", id);
        queue.add(pushInfoMapper.toPushInfo(request, userLogin, id));
    }

    public void collect(PushInfo pushInfo){
        log.info("Collecting push - {}", pushInfo);
        queue.add(pushInfo);
    }

    public PushInfo findInQueue(UUID id) {
        return queue.stream()
                .filter(pi -> pi.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Scheduled(fixedDelay = 1000)
    public void sendToDB() {
        if (queue.isEmpty()) return;
        log.info("Sending push info to database...");
        int batchesCount = 0;

        while (!queue.isEmpty() && batchesCount < MAX_BATCHES) {
            List<PushInfo> batch = new ArrayList<>();

            while (batch.size() < BATCH_SIZE) {
                PushInfo pi = queue.poll();
                if (pi == null) break;
                batch.add(pi);
            }

            if (!batch.isEmpty()) {
                saveBatch(batch);
                batchesCount++;
            }
        }
    }

    private void saveBatch(List<PushInfo> batch) {
        try{
            pushInfoRepository.saveAllAndFlush(batch);
            log.info("Pushes saved successfully, current queue size - {}", queue.size());
        } catch (Exception e){
            log.error("Error saving pushes - {}", e.getMessage());
            queue.addAll(batch);
        }
    }
}
