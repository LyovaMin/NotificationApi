package by.lyofchik.registrationservice.Controller.Rest;

import by.lyofchik.registrationservice.Model.DTO.Request.CompanyRequest;
import by.lyofchik.registrationservice.Model.DTO.Response.Response;
import by.lyofchik.registrationservice.Model.DTO.Request.UserRegistrationRequest;
import by.lyofchik.registrationservice.Service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {
    RegistrationService registrationService;

    @PostMapping("/user")
    public Response registerUser(@RequestBody UserRegistrationRequest request) {
        return registrationService.registerUser(request);
    }

    @PostMapping("/company")
    public Response registerCompany(@RequestBody CompanyRequest request){
        return registrationService.registerCompany(request);
    }
}
