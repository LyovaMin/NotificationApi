package by.lyofchik.pushserver.Model.Enum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChannelType {
    SMS("notifications.sms"),
    WEB("notifications.webpush"),
    ANDROID("notifications.android"),
    IOS("notifications.ios"),
    HUAWEI("notifications.huawei"),
    VIBER("notifications.viber");

    private final String topicName;
}
