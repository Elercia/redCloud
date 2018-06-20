package redcloud.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redcloud.api.dto.DtoMapper;
import redcloud.api.dto.entity.SimpleUserDto;
import redcloud.api.dto.entity.UserDto;
import redcloud.api.route.QueryParam;
import redcloud.api.route.Route;
import redcloud.exceptions.UserNotFoundException;
import redcloud.business.service.UserService;

import java.util.List;
import java.util.UUID;

@Api(value = "Operations about users.", description = "Manage user and get info about them.")
@RestController
public class UserController {

    private UserService userService;
    private DtoMapper dtoMapper;

    @Autowired
    public UserController(UserService userService, DtoMapper dtoMapper) {
        this.userService = userService;
        this.dtoMapper = dtoMapper;
    }

    @ApiOperation(value = "Get the list of all users.")
    @GetMapping(Route.USERS)
//    @RequirePrivilege(value = PrivilegeType.SUPER_ADMIN)
    public List<UserDto> getAllUser() {
        return dtoMapper.entityToDto(userService.getAllUsers());
    }

    @ApiOperation(value = "Create a user")
    @PostMapping(Route.USERS)
    public ResponseEntity<UserDto> createUser(@RequestBody SimpleUserDto wantedUser) throws UserNotFoundException {

        UserDto createdUser = dtoMapper.entityToDto(userService.createUser(wantedUser));

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get one user")
    @GetMapping(Route.USER)
    public UserDto getUser(@RequestParam(QueryParam.USER_ID) UUID userId) throws UserNotFoundException {

        // TODO auth service to check the right of getting on this user
        return dtoMapper.entityToDto(userService.findByResourceId(userId));
    }


}
