package manev.damyan.inventory.inventory.livenesscheck;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;

@Configuration
@Slf4j
public class ThreadSchedulerConfig {

    @Bean(name = "DPMtaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("DPMTestTimeCheckThread-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "DPMtaskScheduler")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(5);
        taskScheduler.setThreadNamePrefix("DPMtaskScheduler");
        taskScheduler.setErrorHandler(t -> log.info("Exception is thrown in the task scheduler of DPM! Please check your implementation!", t));
        return taskScheduler;
    }
}
