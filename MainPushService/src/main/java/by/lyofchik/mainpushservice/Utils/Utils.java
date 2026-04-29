package by.lyofchik.mainpushservice.Utils;

import by.lyofchik.mainpushservice.Model.Entity.User;
import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@AllArgsConstructor
public class Utils {
    private static final Pattern TAG_PATTERN = Pattern.compile("\\{(.+?)}");

    public static List<ChannelType> getChannelTypes(String channelTypes) {
        List<ChannelType> channelTypesList = new ArrayList<>();
        String[] channelTypesArray = channelTypes.split(",");
        for (String channelType : channelTypesArray) {
            try {
                channelTypesList.add(ChannelType.valueOf(channelType.trim()));
            }  catch (IllegalArgumentException e) {
                log.warn("Invalid channel type - {}", channelType);
            }
        }
        return channelTypesList;
    }

    public static void fillMapFromUser(User user, Map<String, String> data){
        data.put("firstName", user.getName());
        data.put("lastName", user.getSurname());
        data.put("middleName", user.getMiddleName());
    }

    public static String fillTemplate(int templateId, Map<String, String> data) {
        String template = Constants.templates.get(templateId);
        if (template == null) {
            log.error("Template not found - {}", templateId);
            return null;
        }

        StringBuilder result = new StringBuilder();
        Matcher matcher = TAG_PATTERN.matcher(template);

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = data.getOrDefault(key, "");

            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(result);

        return result.toString();
    }
}
