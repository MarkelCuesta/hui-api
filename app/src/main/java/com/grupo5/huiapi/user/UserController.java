package com.grupo5.huiapi.user;

import Exceptions.EmailTakenException;
import Exceptions.IncorrectPasswordException;
import Exceptions.UserIdNotFoundException;
import Exceptions.UsernameTakenException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("register")
    public String registerNewUser(@RequestBody User user) {
        try {
            return userService.insertUser(user);
        } catch (EmailTakenException | UsernameTakenException e) {
             throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }
    @DeleteMapping(path = "{id}")
    public String deleteUser(@PathVariable("id") Long id, @RequestBody ObjectNode body) {
        String password = body.get("password").asText();
        System.out.println(password);
        try {
            return userService.deleteUser(id, password);
        } catch (IncorrectPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        } catch (UserIdNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
