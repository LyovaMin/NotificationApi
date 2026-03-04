package by.lyofchik.webpushservice.Configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
@EnableAspectJAutoProxy
public class ExecutorServiceConfig {
    @Bean(name = "push_executor")
    Executor pushExecutor() {
        int cores = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(cores * 2);
        executor.setMaxPoolSize(cores * 8);
        executor.setKeepAliveSeconds(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("task-");
        executor.setAllowCoreThreadTimeOut(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        return executor;
    }
}
