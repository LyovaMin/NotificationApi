package by.lyofchik.mainpushservice.Controller;

import by.lyofchik.mainpushservice.Model.DTO.Request.Templates.TemplatesRequest;
import by.lyofchik.mainpushservice.Model.DTO.Response.Response;
import by.lyofchik.mainpushservice.Service.TemplatesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Templates;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TemplatesController {
    TemplatesService templatesService;

    @PostMapping("/templates")
    public Response templates(@RequestBody TemplatesRequest request){
        return templatesService.templates(request);
    }
}
