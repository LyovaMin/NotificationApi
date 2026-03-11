package by.lyofchik.mainpushservice.Controller;

import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotiRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.CancelRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiListRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotiRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo.GetStatusRq;
import by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo.UpdateStatusRq;
import by.lyofchik.mainpushservice.Model.DTO.Response.Response;
import by.lyofchik.mainpushservice.Service.PushInfoService;
import by.lyofchik.mainpushservice.Service.SendingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PushController {
    SendingService sendingService;
    PushInfoService pushInfoService;

    @PostMapping("/sendPushToUser")
    public Response sendPushToUser(@RequestBody NotiRq request){
        return sendingService.sendPushToSingleUser(request);
    }

    @PostMapping("/sendPushToAllUsers")
    public Response sendPushToAllUsers(@RequestBody AllNotiRq request){
        return sendingService.sendAllPushes(request);
    }

    @PostMapping("/sendPushToListUsers")
    public Response sendPushToAllUsers(@RequestBody NotiListRq request){
        return sendingService.sendListPushes(request);
    }

    @PostMapping("/cancelPushes")
    public Response cancelPushes(@RequestBody CancelRq request){
        return sendingService.cancelPushes(request);
    }

    @PostMapping("/updateStatus")
    public Response updateStatus(@RequestBody UpdateStatusRq request){
        return pushInfoService.updatePushStatus(request);
    }

    @PostMapping("/pushStatus")
    public Response pushStatus(@RequestBody GetStatusRq request){
        return pushInfoService.getButchStatus(request);
    }
}
