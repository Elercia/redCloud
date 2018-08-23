package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.route.Route;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Test operations", description = "Contains all server test")
@RestController
public class TestController {

    @Autowired
    public TestController() {

    }

    @ApiOperation(value = "Realise a ping on the redcloud.api")
    @GetMapping(Route.PING)
    public String ping() {
        return "pong";
    }
}