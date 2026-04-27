package by.lyofchik.webpushservice.Component;

import by.lyofchik.webpushservice.Model.DTO.StatusDto;
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
    private static final int BATCH_SIZE = 1000;
    private static final int MAX_BATCHES = 10;

    public void collect(UUID pushId, PushStatus status) {
        log.info("Collecting status for pushId={}", pushId);
        StatusDto statusDto = new StatusDto(pushId, status);
        queue.add(statusDto);
    }

    @Scheduled(fixedDelay = 1000)
    private void sendToDB() {
        if (queue.isEmpty()) return;

        int batchCount = 0;

        while (!queue.isEmpty() && batchCount < MAX_BATCHES) {
            List<StatusDto> batch = new ArrayList<>();

            while (batch.size() < BATCH_SIZE) {
                StatusDto dto = queue.poll();
                if (dto == null) break;
                batch.add(dto);
            }

            if (!batch.isEmpty()) {
                saveBatch(batch);
                batchCount++;
            }
        }
    }

    private void saveBatch(List<StatusDto> batch) {
        try {
            for (StatusDto dto : batch) {
                pushInfoRepository.updateStatusSecure(dto.getPushId(), dto.getStatus().toString());
            }

            log.info("Pushes saved successfully, current queue size - {}", queue.size());
        }  catch (Exception e) {
            log.error("Error saving pushes - {}", e.getMessage());
            queue.addAll(batch);
        }
    }
}