package by.lyofchik.mainpushservice.Service;

import by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo.PushGetStatusRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo.PushUpdateStatusRequest;
import by.lyofchik.mainpushservice.Model.DTO.Response.Response;
import by.lyofchik.mainpushservice.Model.Entity.PushInfo;
import by.lyofchik.mainpushservice.Repository.PushInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PushInfoService {
    PushInfoRepository pushInfoRepository;

    public Response getButchStatus(PushGetStatusRequest request) {
        List<PushInfo> pushInfos = pushInfoRepository.findPushInfoByBatch(request.getBatchId());
        return Response.success(pushInfos);
    }

    public Response updatePushStatus(PushUpdateStatusRequest request) {
        PushInfo pushInfo = pushInfoRepository.findById(request.getPushId());
        pushInfo.setStatus(request.getPushStatus());
        pushInfoRepository.save(pushInfo);
        return Response.success();
    }
}
