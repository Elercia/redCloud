package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.route.Route;
import fr.elercia.redcloud.business.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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