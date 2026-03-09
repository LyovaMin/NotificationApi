package by.lyofchik.webpushservice.Service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TemplateService {
    public String fillTemplate(String template, Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            template = template.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return template;
    }
}
