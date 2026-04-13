package by.lyofchik.mainpushservice.Utils;

import by.lyofchik.mainpushservice.Model.Enum.ChannelType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Utils {
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
}
