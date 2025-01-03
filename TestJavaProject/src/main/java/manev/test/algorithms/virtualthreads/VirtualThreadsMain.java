package manev.test.algorithms.virtualthreads;

import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VirtualThreadsMain {
    public static void main(String[] args) throws InterruptedException {

        ThreadLocal<SimpleDateFormat> formatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat());

        ExecutorService executorService = Executors.newThreadPerTaskExecutor(new ThreadFactory() {

            private AtomicInteger counter = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                ThreadFactory factory = Thread.ofVirtual().factory();
                Thread thread = factory.newThread(r);
                thread.setName("DPMVirtThreadTest-" + counter.getAndIncrement());
                return thread;
            }
        });

        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 10; ++i) {

            Thread.ofVirtual().start(() -> {
                Instant end = Instant.now().plus(5, ChronoUnit.SECONDS);
//                int currentTaskId = atomicInteger.getAndIncrement();
                String currentTaskId = UUID.randomUUID().toString();

                System.out.println("Starting task with id: " + currentTaskId  );


                while(Instant.now().compareTo(end) < 0){

                }

                System.out.println("Completed task with id: " + currentTaskId + " end time is: " + end);
            });
        }

        FileChannel ch;


        Thread.sleep(100000);
    }
}
