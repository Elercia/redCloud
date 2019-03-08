package fr.elercia.redcloud.stresstest;

import fr.elercia.redcloud.Application;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
public class StressTest {

    private static final Logger LOG = LoggerFactory.getLogger(StressTest.class);

    public static final String ROOT_URI = "http://localhost:8080";
    public static final String USER_NAME_PREFIX = "stressUser";

    private static final int NUMBER_OF_THREADS = 100;
    public static final int NUMBER_OF_FILES_TO_UPLOAD = 100;

    @Test
    void stressTest() throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Boolean>> callables = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            callables.add(new StressTestTask(i));
        }

        List<Future<Boolean>> futures = executorService.invokeAll(callables);

        while (!isFinished(futures)) {
            Thread.sleep(100);
            LOG.info("Process not finished (elapsed time {})", System.currentTimeMillis() - startTime);
        }

        long endTime = System.currentTimeMillis();

        long cancelledTaskNumber = futures.stream().filter(f -> {
            try {
                return !f.get();
            } catch (Throwable e) {
                return true;
            }
        }).count();
        LOG.info("Finished running {} tasks ({} aborted), elapsed time {} ms", NUMBER_OF_THREADS, cancelledTaskNumber, endTime - startTime);

        assertEquals(0, cancelledTaskNumber);
    }

    private boolean isFinished(List<Future<Boolean>> futures) {
        for (Future<Boolean> future : futures) {
            if (!future.isDone())
                return false;
        }

        return true;
    }
}
