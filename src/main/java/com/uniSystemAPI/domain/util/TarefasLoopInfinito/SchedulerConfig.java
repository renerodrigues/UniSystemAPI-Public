package com.uniSystemAPI.domain.util.TarefasLoopInfinito;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5); // Defina o tamanho do pool conforme necessário
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return scheduler;
    }
}
