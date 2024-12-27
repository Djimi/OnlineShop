package manev.damyan.inventory.inventory.livenesscheck;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@Data
public class LivenessLogService {

    private final TimeService timeService;

    private final AtomicInteger counter = new AtomicInteger(1);

//    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS, scheduler = "DPMtaskScheduler")
    public void logLiveness() throws InterruptedException, ExecutionException {

        //        String currentTimestamp = ZonedDateTime.now().toString();

        int currentCounterValue = counter.getAndIncrement();
        log.info("Start checking the time in liveness services for: " + currentCounterValue);
        String currentTimestamp = timeService.getCurrentTime(currentCounterValue).get();

        log.info("Completed checking the time in liveness services for: " + currentCounterValue);
        //        log.info("Start of: " + currentTimestamp);
        log.info("Service is live at: " + currentTimestamp + ". Executed on thread: " + Thread.currentThread().getName());
        Thread.sleep(10000);
        //        log.info("End of: " + currentTimestamp);
    }

    //    @Async
    //    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.SECONDS)
    //    public void logLiveness2() throws InterruptedException {
    //
    //        String currentTimestamp = ZonedDateTime.now().toString();
    //
    //        //        log.info("Start of: " + currentTimestamp);
    //        log.info("TestMethod: " + currentTimestamp);
    //        //        log.info("End of: " + currentTimestamp);
    //    }
}
