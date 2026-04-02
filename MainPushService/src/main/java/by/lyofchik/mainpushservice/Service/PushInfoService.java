package by.lyofchik.mainpushservice.Service;

import by.lyofchik.mainpushservice.Component.PushInfoCollector;
import by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo.GetStatusRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo.UpdateStatusRq;
import by.lyofchik.mainpushservice.Model.DTO.Response.Response;
import by.lyofchik.mainpushservice.Model.Entity.PushInfo;
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

    public Response getButchStatus(GetStatusRq request) {
        log.info("getButchStatus - {}", request);
        List<PushInfo> pushInfos = pushInfoRepository.findPushInfoByBatch(request.getBatchId());
        return Response.success(pushInfos);
    }

    public Response updatePushStatus(UpdateStatusRq request) {
        log.info("updatePushStatus - {}", request);

        PushInfo pi = pushInfoRepository.findById(request.getPushId()).orElse(null);
        if (Objects.nonNull(pi)) {
            pi.setStatus(request.getPushStatus());
            pushInfoRepository.save(pi);
            log.info("Status updated in DB to: {}", pi.getStatus());
            return Response.success();
        }

        pi = pushInfoCollector.findInQueue(request.getPushId());
        if (Objects.nonNull(pi)) {
            pi.setStatus(request.getPushStatus());
            log.info("Status updated in Queue to: {}", pi.getStatus());
            return Response.success();
        }

        log.warn("Push not found");
        return Response.error();
    }
}
