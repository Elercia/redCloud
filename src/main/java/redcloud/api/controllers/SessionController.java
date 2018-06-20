package redcloud.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redcloud.api.dto.entity.SessionDto;
import redcloud.api.route.Route;
import redcloud.exceptions.WrongLoginException;
import redcloud.business.service.AuthorizationService;

@Api(value = "Operations session.", description = "Allow to login and logout user with session tokens.")
@RestController
public class SessionController {

    private AuthorizationService authorizationService;

    @Autowired
    public SessionController(AuthorizationService authorizationService) {

        this.authorizationService = authorizationService;
    }

    @ApiOperation(value = "Login a user.", response = SessionDto.class)
    @PostMapping(Route.LOGIN)
    public ResponseEntity<SessionDto> login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) throws WrongLoginException {

        String token = authorizationService.login(username, password);
        SessionDto sessionDto = new SessionDto(token);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("token", token)
                .body(sessionDto);
    }

    @ApiOperation(value = "Login a user.")
    @PostMapping(Route.LOGOUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestHeader(value = "token", required = false) String token) {

        authorizationService.logout(token);
    }
}
