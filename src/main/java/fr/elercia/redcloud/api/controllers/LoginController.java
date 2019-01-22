package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.LoginDto;
import fr.elercia.redcloud.api.dto.entity.TokenDto;
import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.business.service.AuthenticationService;
import fr.elercia.redcloud.api.security.PermitAll;
import fr.elercia.redcloud.exceptions.InvalidLoginException;
import fr.elercia.redcloud.exceptions.TokenNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "Operations to login and logout")
@RestController
@RequestMapping("/")
public class LoginController extends ControllerUtils {

    private AuthenticationService authenticationService;

    @Autowired
    public LoginController(AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }

    @PostMapping(Route.LOGIN)
    @ApiOperation(value = "Authenticate user with Bearer Auth", consumes = "application/json")
    @PermitAll
    public TokenDto login(@RequestBody LoginDto loginDto) throws InvalidLoginException {

        Token token = authenticationService.login(loginDto.getUsername(), loginDto.getPassword());

        return DtoMapper.map(token);
    }

    @GetMapping(Route.LOGOUT)
    @ApiOperation(value = "Revoke a token")
    public void logout() {

        authenticationService.logout(getAuthToken());
    }
}
