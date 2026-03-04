package by.lyofchik.pushserver.Service;

import by.lyofchik.pushserver.Model.DTO.Request.CompanyRequest;
import by.lyofchik.pushserver.Model.DTO.Response.Response;
import by.lyofchik.pushserver.Model.DTO.Request.UserRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    public Response registerUser(UserRegistrationRequest request) {


        return Response.success();
    }

    public Response registerCompany(CompanyRequest request) {


        return Response.success();
    }
}
