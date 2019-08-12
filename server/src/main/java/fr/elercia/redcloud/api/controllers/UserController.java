package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.controllers.params.Parameters;
import fr.elercia.redcloud.api.controllers.params.QueryParam;
import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.SimpleUserDto;
import fr.elercia.redcloud.api.dto.entity.UserDto;
import fr.elercia.redcloud.api.security.RequireUserType;
import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.business.service.SecurityUtils;
import fr.elercia.redcloud.business.service.UserService;
import fr.elercia.redcloud.exceptions.InvalidUserCreationException;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api(value = "Operations about users.")
@RestController
@RequestMapping("/")
public class UserController extends AbstractController {

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

        return DtoMapper.entityToDto(userService.findAllUsers());
    }

    @ApiOperation(value = "Find user by name")
    @GetMapping(Route.USERS_SEARCH)
    public UserDto search(@RequestParam(Parameters.USER_SEARCH_NAME) String researchedName) throws UserNotFoundException {

        LOG.info("findByName {}", researchedName);

        AppUser user = userService.findByName(researchedName);

        return DtoMapper.entityToDto(user);
    }

    @ApiOperation(value = "Get one user")
    @GetMapping(Route.USER)
    public UserDto getUser(@PathVariable(QueryParam.USER_ID) UUID userId) throws UserNotFoundException {

        if (userId.equals(UUID.fromString(""))) {
            userId = getConnectedUserUid();
        }

        LOG.info("getUser {} - connected user id {} ", userId, getConnectedUserUid());

        return DtoMapper.entityToDto(userService.findByResourceId(userId));
    }

    @ApiOperation(value = "Delete user")
    @DeleteMapping(Route.USER)
    @RequireUserType(UserType.ADMIN)
    public void deleteUser(@PathVariable(QueryParam.USER_ID) UUID userId) throws UserNotFoundException {

        LOG.info("deleteUser {}", userId);

        AppUser user = userService.findByResourceId(userId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), user);

        userService.deleteUser(user);
    }

    @ApiOperation(value = "Update user")
    @PutMapping(Route.USER)
    public UserDto updateUser(@PathVariable(QueryParam.USER_ID) UUID userId, @RequestBody SimpleUserDto updateUserDto) throws UserNotFoundException {

        LOG.info("updateUser {}", userId);

        AppUser user = userService.findByResourceId(userId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), user);

        return DtoMapper.entityToDto(userService.updateUser(user, updateUserDto));
    }

    @ApiOperation(value = "Create a from a connected user ")
    @PostMapping(Route.USERS)
    public ResponseEntity<UserDto> createUser(@RequestBody SimpleUserDto creationUserDto) throws InvalidUserCreationException {

        UUID connectedUserUid = getConnectedUserUid();
        LOG.info("deleteUser {}", connectedUserUid);

        AppUser user = userService.createUser(connectedUserUid, creationUserDto);

        return new ResponseEntity<>(DtoMapper.entityToDto(user), HttpStatus.CREATED);
    }
}
