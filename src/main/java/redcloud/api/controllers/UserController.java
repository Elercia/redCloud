package redcloud.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redcloud.api.route.Route;
import redcloud.entity.User;
import redcloud.services.UserService;

import java.util.List;

@Api(value = "Operations about users.", description = "Manage user and get info about them.")
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @ApiOperation(value = "Get the list of all users.")
    @GetMapping(Route.USERS)
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }
}
