package manev.damyan.inventory.inventory.livenesscheck;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class TimeService {

    @Async("DPMtaskExecutor")
    public CompletableFuture<String> getCurrentTime(int requestId) throws InterruptedException {
        log.info("Start checking the time in TimeService for request:" + requestId + " on thread: " + Thread.currentThread().getName());
        Thread.sleep(3000);
        return CompletableFuture.completedFuture(ZonedDateTime.now().toString());
    }
}





