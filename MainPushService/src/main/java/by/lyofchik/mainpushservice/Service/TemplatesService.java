package by.lyofchik.mainpushservice.Service;

import by.lyofchik.mainpushservice.Model.DTO.Response.Response;
import by.lyofchik.mainpushservice.Model.Entity.Template;
import by.lyofchik.mainpushservice.Repository.TemplateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class TemplatesService {
    TemplateRepository templateRepository;

    public Response templates(int companyId) {
        List<Template> templates = templateRepository.findAllByCompany(companyId);
        return Response.success(templates);
    }
}
