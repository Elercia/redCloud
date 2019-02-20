package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.security.PermitAll;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;

@Api(value = "Test operations", description = "Contains all server test")
@RestController
@RequestMapping("/")
public class TestController extends AbstractController {

    @Autowired
    public TestController() {
        // Add all test dependencies
    }

    @ApiOperation(value = "Realise a ping on the redcloud api")
    @GetMapping(Route.PING)
    @PermitAll
    public String ping() {
        return "pong";
    }

    @ApiOperation(value = "Test api. Input and outputs will change through time.")
    @GetMapping(Route.TEST_API)
    public Enumeration<String> test() {
        return getRequest().getHeaderNames();
    }
}