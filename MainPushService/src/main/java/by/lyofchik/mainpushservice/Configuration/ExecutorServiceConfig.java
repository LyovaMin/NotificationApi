package by.lyofchik.mainpushservice.Configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Slf4j
@Configuration
@EnableAspectJAutoProxy
public class ExecutorServiceConfig {
    @Bean(name = "executor")
    Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setKeepAliveSeconds(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("task-");

        return executor;
    }
}