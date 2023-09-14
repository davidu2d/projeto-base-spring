package com.u2d.projeto.config.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "asyncDefaultExecutor")
    public Executor asyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(5);
        executor.setThreadNamePrefix("ThreadAppExecutor-");
        return executor;
    }

    @Bean(name = "getCNPJExecutor")
    public Executor getCNPJExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(20);
        executor.setThreadNamePrefix("ThreadGetCNPJExecutor-");
        return executor;
    }
}
