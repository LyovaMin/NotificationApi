package by.lyofchik.webpushservice.Utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class Utils {
    public static Set<String> getSubscriptionErrors(){
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("application.properties")) {
            props.load(input);
            String errors = props.getProperty("errors.subscriptions");
            return Set.of(errors.split(","));
        } catch (Exception e) {
            return Set.of("404", "410");
        }
    }

    public static Set<String> getRetriableErrors(){
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("application.properties")) {
            props.load(input);
            String errors = props.getProperty("errors.retriable");
            return Set.of(errors.split(","));
        } catch (Exception e) {
            return Set.of("429", "500", "503");
        }
    }
}
