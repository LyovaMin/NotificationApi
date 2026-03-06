package by.lyofchik.registrationservice.Service;

import by.lyofchik.registrationservice.Model.DTO.Request.CompanyRequest;
import by.lyofchik.registrationservice.Model.DTO.Response.Response;
import by.lyofchik.registrationservice.Model.DTO.Request.UserRegistrationRequest;
import by.lyofchik.registrationservice.Model.Entity.Company;
import by.lyofchik.registrationservice.Model.Entity.User;
import by.lyofchik.registrationservice.Repository.CompanyRepository;
import by.lyofchik.registrationservice.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RegistrationService {
    UserRepository userRepository;
    CompanyRepository companyRepository;

    public Response registerUser(UserRegistrationRequest request) {
        if(request == null){
            log.error("registerUser request is null");
            return Response.error();
        }

        User user = new User(request.getLogin(), request.getCompanyId());
        userRepository.save(user);

        return Response.success();
    }

    public Response registerCompany(CompanyRequest request) {
        if(request == null){
            log.error("registerCompany request is null");
            return Response.error();
        }
        Company company = Company.builder()
                .companyName(request.getCompanyName())
                .build();
        companyRepository.save(company);

        return Response.success();
    }
}
