package redcloud.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import redcloud.api.route.Route;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import redcloud.business.services.TestService;

@Api(value="Test operations", description="Contains all server test")
@RestController
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @ApiOperation(value = "Realise a ping on the redcloud.api")
    @GetMapping(Route.PING)
    public String ping() {
        return "pong";
    }

    @ApiOperation(value = "Realise a ping on the redcloud.api")
    @GetMapping(Route.TEST_SERVICE)
    public String useService() {
        return testService.doSomething();
    }
}