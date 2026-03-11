package by.lyofchik.mainpushservice.Configuration;

import feign.Logger;
import feign.Request;
import org.springframework.cloud.openfeign.clientconfig.FeignClientConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableAspectJAutoProxy
public class FeignConfig implements FeignClientConfigurer {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(
                5L, TimeUnit.SECONDS,
                10L, TimeUnit.SECONDS,
                true
        );
    }

    // ErrorDecoder обработчик ошибкок exception decoder
    // RequestInterceptor добавление заголовков типо "authorization"
    // Retryer для повторных попыток подключения
}