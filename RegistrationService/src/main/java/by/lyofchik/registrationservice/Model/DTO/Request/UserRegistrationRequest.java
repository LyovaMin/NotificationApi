package by.lyofchik.registrationservice.Model.DTO.Request;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    String login;
    int companyId;
}
