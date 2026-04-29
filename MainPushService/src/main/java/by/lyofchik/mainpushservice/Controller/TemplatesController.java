package by.lyofchik.mainpushservice.Controller;

import by.lyofchik.mainpushservice.Model.DTO.Response.Response;
import by.lyofchik.mainpushservice.Service.TemplatesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TemplatesController {
    TemplatesService templatesService;

    @PostMapping("/templates/{companyId}")
    public Response templates(@PathVariable int companyId){
        return templatesService.templates(companyId);
    }
}
