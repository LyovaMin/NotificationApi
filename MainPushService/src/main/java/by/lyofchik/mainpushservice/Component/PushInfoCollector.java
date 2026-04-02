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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
@Slf4j
public class PushInfoCollector {
    private PushInfoMapper pushInfoMapper;
    private PushInfoRepository pushInfoRepository;
    private final Map<UUID, PushInfo> buffer = new ConcurrentHashMap<>();
    private static final int BATCH_SIZE = 1000;
    private static final int MAX_BATCHES = 10;

    public void collect(AllNotiRq request, String userLogin, UUID id) {
        log.info("Collecting push - {}", id);
        buffer.put(id, pushInfoMapper.toPushInfo(request, userLogin, id));
    }

    public void collect(NotiListRq request, String userLogin, UUID id) {
        log.info("Collecting push - {}", id);
        buffer.put(id, pushInfoMapper.toPushInfo(request, userLogin, id));
    }

    public void collect(NotiRq request, String userLogin, UUID id) {
        log.info("Collecting push - {}", id);
        buffer.put(id, pushInfoMapper.toPushInfo(request, userLogin, id));
    }

    public PushInfo getPushInfo(UUID id) {
        return buffer.get(id);
    }

    @Scheduled(fixedDelay = 1000)
    public void sendToDB() {
        if (buffer.isEmpty()) return;
        log.info("Sending push info to database...");
        int batchesCount = 0;
        while (batchesCount < MAX_BATCHES && !buffer.isEmpty()) {
            List<UUID> keysBatch = buffer.keySet().stream()
                    .limit(BATCH_SIZE)
                    .toList();

            List<PushInfo> batch = new ArrayList<>();
            for (UUID id : keysBatch) {
                PushInfo pushInfo = buffer.remove(id);
                if (Objects.nonNull(pushInfo)) {
                    batch.add(pushInfo);
                }
            }

            if(!batch.isEmpty()) {
                saveBatch(batch);
                batchesCount++;
            }
        }
    }

    private void saveBatch(List<PushInfo> batch) {
        try{
            pushInfoRepository.saveAllAndFlush(batch);
            log.info("Pushes saved successfully, current queue size - {}", buffer.size());
        } catch (Exception e){
            log.error("Error saving pushes - {}", e.getMessage());
            batch.forEach(pi -> buffer.putIfAbsent(pi.getId(), pi));
        }
    }
}
