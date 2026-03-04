package by.lyofchik.pushserver.Controller.Rest;

import by.lyofchik.pushserver.Model.DTO.Request.DeleteAllSubsRequest;
import by.lyofchik.pushserver.Model.DTO.Response.Response;
import by.lyofchik.pushserver.Model.DTO.Request.SubscriptionAddRequest;
import by.lyofchik.pushserver.Model.DTO.Request.SubscriptionDeleteRequest;
import by.lyofchik.pushserver.Service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
@AllArgsConstructor
public class SubscriptionController {
    SubscriptionService subscriptionService;

    @PostMapping("/add")
    public Response addSubscription(@RequestBody SubscriptionAddRequest request) {
        return subscriptionService.addSubscription(request);
    }

    @PostMapping("/delete")
    public Response deleteSubscription(@RequestBody SubscriptionDeleteRequest request){
        return subscriptionService.deleteSubscription(request);
    }

    @PostMapping("/deleteAll")
    public Response deleteAllSubscriptions(@RequestBody DeleteAllSubsRequest request){
        return subscriptionService.deleteAllSubscriptions(request);
    }
}
