package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.dto.entity.LoginDto;
import fr.elercia.redcloud.api.route.Route;
import fr.elercia.redcloud.exceptions.WrongLoginException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "Operations to login and logout", description = "Manager auth session")
@RestController
@RequestMapping("/")
public class LoginController {


    @Autowired
    public LoginController() {

    }

    @PostMapping(Route.LOGIN)
    @ApiOperation(value = "Get a token from a user")
    public String login(@RequestBody LoginDto loginDto) throws WrongLoginException {
        return null;
    }

    @GetMapping(Route.LOGOUT)
    @ApiOperation(value = "Revoke a token")
    public void logout() {

    }
}
