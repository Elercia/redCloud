package fr.elercia.redcloud.stresstest;

import com.google.common.collect.Lists;
import fr.elercia.redcloud.Application;
import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.dto.entity.LoginDto;
import fr.elercia.redcloud.api.dto.entity.TokenDto;
import fr.elercia.redcloud.api.dto.entity.UserDto;
import fr.elercia.redcloud.config.SecurityConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

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

    private static final int NUMBER_OF_THREADS = 10;
    public static final int NUMBER_OF_FILES_TO_UPLOAD = 100;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();


        ResponseEntity<TokenDto> response = restTemplate.postForEntity(StressTest.ROOT_URI + Route.LOGIN, new LoginDto("admin", "password"), TokenDto.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException();
        }

        restTemplate.setInterceptors(Lists.newArrayList((ClientHttpRequestInterceptor) (request, body, execution) -> {
            request.getHeaders().set(SecurityConstants.REQUEST_HEADER_NAME, SecurityConstants.TOKEN_TYPE + " " + response.getBody().getAccessToken());
            return execution.execute(request, body);
        }));

        ResponseEntity<UserDto[]> responseEntity = restTemplate.getForEntity(ROOT_URI + Route.USERS, UserDto[].class);
        for (UserDto user : responseEntity.getBody()) {
            if (user.getName().startsWith(USER_NAME_PREFIX)) {
                try {
                    restTemplate.delete(StressTest.ROOT_URI + Route.USER, user.getResourceId());
                } catch (Throwable e) {

                }
            }
        }
    }

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
