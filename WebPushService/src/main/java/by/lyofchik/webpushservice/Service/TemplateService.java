package by.lyofchik.webpushservice.Service;

import by.lyofchik.webpushservice.Model.Entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class TemplateService {
    private final Map<String, Field> USER_FIELDS = new HashMap<>();
    private final Pattern PATTERN = Pattern.compile("\\{(.+?)}");

    @PostConstruct
    void init() {
        for (Field field : User.class.getDeclaredFields()) {
            field.setAccessible(true);
            USER_FIELDS.put(field.getName(), field);
        }
    }

    public String fillTemplate(String template, User user) throws IllegalAccessException {
        for (Map.Entry<String, Field> entry : USER_FIELDS.entrySet()) {
            Object value = entry.getValue().get(user);
            String placeholder = "{" + entry.getKey() + "}";
            String replacement = (value != null) ? value.toString() : "";
            template = template.replace(placeholder, replacement);
        }

        return template;
    }
}