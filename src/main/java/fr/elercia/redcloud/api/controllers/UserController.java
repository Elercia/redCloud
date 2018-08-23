package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.CreateUserDto;
import fr.elercia.redcloud.api.dto.entity.UserDto;
import fr.elercia.redcloud.api.route.QueryParam;
import fr.elercia.redcloud.api.route.Route;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.service.UserService;
import fr.elercia.redcloud.exceptions.InvalidUserCreationException;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import fr.elercia.redcloud.logging.LoggerWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(value = "Operations about users.", description = "Manage user and get info about them.")
@RestController
public class UserController {

    private static final LoggerWrapper LOG = new LoggerWrapper(UserController.class);

    private UserService userService;
    private DtoMapper dtoMapper;

    @Autowired
    public UserController(UserService userService, DtoMapper dtoMapper) {
        this.userService = userService;
        this.dtoMapper = dtoMapper;
    }

    @ApiOperation(value = "Get the list of all users.")
    @GetMapping(Route.USERS)
    public List<UserDto> getAllUser() {

        LOG.info("getAllUser");

        return dtoMapper.entityToDto(userService.getAllUsers());
    }

    @ApiOperation(value = "Create a user")
    @PostMapping(name = Route.USERS, consumes = {"application/json"})
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto wantedUser) throws InvalidUserCreationException {

        LOG.info("createUser", "name", wantedUser.getName());

        UserDto createdUser = dtoMapper.entityToDto(userService.createUser(wantedUser));

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Find user by name")
    @GetMapping(Route.USERS_NAME)
    public List<UserDto> findByName(@RequestParam String researchedName) throws UserNotFoundException {

        LOG.info("findByName", "name", researchedName);

        List<User> users = userService.findByName(researchedName);

        return dtoMapper.entityToDto(users);
    }

    @ApiOperation(value = "Get one user")
    @GetMapping(Route.USER)
    public UserDto getUser(@RequestParam(QueryParam.USER_ID) UUID userId) throws UserNotFoundException {

        LOG.info("getUser", "userId", userId);

        // TODO auth service to check the right of getting on this user
        return dtoMapper.entityToDto(userService.findByResourceId(userId));
    }


}
