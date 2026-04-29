package by.lyofchik.mainpushservice.Utils;

import by.lyofchik.mainpushservice.Model.Entity.Template;
import by.lyofchik.mainpushservice.Repository.TemplateRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Constants {
    private TemplateRepository templateRepository;
    static final Map<Integer, String> templates = new HashMap<>();

    @PostConstruct
    public void init() {
        List<Template> templateList = templateRepository.findAll();
        for (Template template : templateList) {
            templates.put(template.getTemplateId(), template.getBody());
        }
    }
}
