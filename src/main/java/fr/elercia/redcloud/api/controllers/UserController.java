package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.controllers.params.Parameters;
import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.CreateUserDto;
import fr.elercia.redcloud.api.dto.entity.UpdateUserDto;
import fr.elercia.redcloud.api.dto.entity.UserDto;
import fr.elercia.redcloud.api.controllers.params.QueryParam;
import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.service.UserService;
import fr.elercia.redcloud.exceptions.InvalidUserCreationException;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.print.PageRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(value = "Operations about users.", description = "Manage user and get info about them.")
@RestController
@RequestMapping("/")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get the list of all users.")
    @GetMapping(Route.USERS)
    public List<UserDto> getAllUser() {

        LOG.info("getAllUser");

        return DtoMapper.entityToDto(userService.getAllUsers());
    }

    @ApiOperation(value = "Create a user")
    @PostMapping(Route.USERS)
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserDto wantedUser) throws InvalidUserCreationException {

        LOG.info("createUser", "name", wantedUser.getName());

        UserDto createdUser = DtoMapper.entityToDto(userService.createUser(wantedUser));

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Find user by name")
    @GetMapping(Route.USERS_SEARCH)
    public UserDto findByName(@RequestParam(Parameters.USER_SEARCH_NAME) String researchedName) throws UserNotFoundException {

        LOG.info("findByName", "name", researchedName);

        User user = userService.findByName(researchedName);

        return DtoMapper.entityToDto(user);
    }

    @ApiOperation(value = "Get one user")
    @GetMapping(Route.USER)
    public UserDto getUser(@PathVariable(QueryParam.USER_ID) UUID userId) throws UserNotFoundException {

        LOG.info("getUser", "userId", userId);

        return DtoMapper.entityToDto(userService.findByResourceId(userId));
    }

    @ApiOperation(value = "Delete user")
    @DeleteMapping(Route.USER)
    public void deleteUser(@PathVariable(QueryParam.USER_ID) UUID userId) throws UserNotFoundException {

        LOG.info("deleteUser", "userId", userId);

       User user = userService.findByResourceId(userId);

       userService.deleteUser(user);
    }

    @ApiOperation(value = "Update user")
    @PutMapping(Route.USER)
    public UserDto updateUser(@PathVariable(QueryParam.USER_ID) UUID userId, @RequestBody UpdateUserDto updateUserDto) throws UserNotFoundException {

        LOG.info("updateUser", "userId", userId);

        User user = userService.findByResourceId(userId);

        return DtoMapper.entityToDto(userService.updateUser(user, updateUserDto));
    }
}
