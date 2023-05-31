package com.capstone.interactive_novel.fcm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Slf4j
@EnableAsync
@Configuration
public class AsyncConfiguration {
    @Bean(name = "fcmAsyncConfiguration")
    public Executor fcmThreadPoolExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int processors = Runtime.getRuntime().availableProcessors();
        executor.setThreadNamePrefix("Async-");
        executor.setCorePoolSize(processors / 2);
        executor.setMaxPoolSize(processors / 2);
        executor.setQueueCapacity(processors / 2);
        executor.initialize();

        log.info("FCM 비동기 설정이 완료되었습니다.\n사용 스레드 수 :" + processors / 2);
        return executor;
    }

}
