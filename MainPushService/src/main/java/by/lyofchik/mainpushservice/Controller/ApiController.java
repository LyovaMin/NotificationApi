package by.lyofchik.mainpushservice.Controller;

import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.CancelRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.AllNotificationsRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.Notification.NotificationsListRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo.PushGetStatusRequest;
import by.lyofchik.mainpushservice.Model.DTO.Request.PushInfo.PushUpdateStatusRequest;
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
public class ApiController {
    SendingService sendingService;
    PushInfoService pushInfoService;

    @PostMapping("/sendPushToUser")
    public Response sendPushToUser(@RequestBody NotificationRequest request){
        return sendingService.sendPushToSingleUser(request);
    }

    @PostMapping("/sendPushToAllUsers")
    public Response sendPushToAllUsers(@RequestBody AllNotificationsRequest request){
        return sendingService.sendAllPushes(request);
    }

    @PostMapping("/sendPushToListUsers")
    public Response sendPushToAllUsers(@RequestBody NotificationsListRequest request){
        return sendingService.sendListPushes(request);
    }

    @PostMapping("/cancelPushes")
    public Response cancelPushes(@RequestBody CancelRequest request){
        return sendingService.cancelPushes(request);
    }

    @PostMapping("/updateStatus")
    public Response updateStatus(@RequestBody PushUpdateStatusRequest request){
        return pushInfoService.updatePushStatus(request);
    }

    @PostMapping("/pushStatus")
    public Response pushStatus(@RequestBody PushGetStatusRequest request){
        return pushInfoService.getButchStatus(request);
    }
}
