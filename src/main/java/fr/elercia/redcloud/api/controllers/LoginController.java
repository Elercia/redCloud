package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.dto.entity.LoginDto;
import fr.elercia.redcloud.api.route.Route;
import fr.elercia.redcloud.business.service.AuthenticationService;
import fr.elercia.redcloud.business.service.security.PermitAll;
import fr.elercia.redcloud.exceptions.WrongLoginException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "Operations to login and logout", description = "Manager auth session")
@RestController
@RequestMapping("/")
public class LoginController extends ControllerUtils{


    private AuthenticationService authenticationService;

    @Autowired
    public LoginController(AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }

    @PostMapping(Route.LOGIN)
    @ApiOperation(value = "Get a token from a user")
    @PermitAll
    public String login(@RequestBody LoginDto loginDto) throws WrongLoginException {

        String token = authenticationService.login(loginDto.getUsename(), loginDto.getPassword());

        return token;
    }

    @GetMapping(Route.LOGOUT)
    @ApiOperation(value = "Revoke a token")
    public boolean logout() {

        return authenticationService.logout(getAuthToken());
    }
}
