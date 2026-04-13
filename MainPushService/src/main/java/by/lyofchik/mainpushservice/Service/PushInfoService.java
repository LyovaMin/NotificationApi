package by.lyofchik.mainpushservice.Service;

import by.lyofchik.mainpushservice.Component.PushInfoCollector;
import by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo.GetStatusRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo.UpdateStatusRq;
import by.lyofchik.mainpushservice.Model.DTO.Response.Response;
import by.lyofchik.mainpushservice.Model.Entity.Batch;
import by.lyofchik.mainpushservice.Model.Entity.PushInfo;
import by.lyofchik.mainpushservice.Model.Enum.BatchStatus;
import by.lyofchik.mainpushservice.Repository.BatchRepository;
import by.lyofchik.mainpushservice.Repository.PushInfoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class PushInfoService {
    PushInfoRepository pushInfoRepository;
    PushInfoCollector pushInfoCollector;
    BatchRepository batchRepository;

    public Response getButchStatus(GetStatusRq request) {
        log.info("getButchStatus - {}", request);
        List<PushInfo> pushInfos = pushInfoRepository.findPushInfoByBatch(request.getBatchId());
        return Response.success(pushInfos);
    }

    public Response updatePushStatus(UpdateStatusRq request) {
        log.info("updatePushStatus - {}", request);

        int updatedStatuses = pushInfoRepository.updateStatus(request.getPushId(), request.getPushStatus());

        if (updatedStatuses > 0) {
            log.info("Status updated in DB to: {}", request.getPushStatus());
            return Response.success();
        }

        PushInfo pi = pushInfoCollector.getPushInfo(request.getPushId());
        if (Objects.nonNull(pi)) {
            pi.setStatus(request.getPushStatus());
            log.info("Status updated in Queue to: {}", pi.getStatus());
            return Response.success();
        }

        log.warn("Push not found");
        return Response.error();
    }

    public Response cancelPushes(int batchId) {
        log.info("cancelPushes - {}", batchId);
        Batch batch = batchRepository.findById(batchId);
        if (batch == null) {
            log.error("cancelPushes - batch is null");
            return Response.error();
        }
        batch.setStatus(BatchStatus.CANCELLED);
        batchRepository.saveAndFlush(batch);

        return Response.success();
    }
}
