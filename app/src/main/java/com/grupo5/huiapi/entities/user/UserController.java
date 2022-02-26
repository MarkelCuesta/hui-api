package com.grupo5.huiapi.entities.user;

import Exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        } catch (IncorrectPasswordException | UserIdNotFoundException e) {
            HttpStatus status = e instanceof IncorrectPasswordException ? HttpStatus.UNAUTHORIZED : HttpStatus.NOT_FOUND;
            throw new ResponseStatusException(status, e.getMessage(), e);
        }
    }
    @PutMapping(path = "{id}")
    public String updateUser(@PathVariable("id") Long id, @RequestBody ObjectNode body) {
        ObjectMapper mapper = new ObjectMapper();
        String password = body.get("password").asText();
        try {
            User user = mapper.treeToValue(body.get("user"), User.class);
            return userService.updateUser(id, password, user);
        } catch (IncorrectPasswordException | UserIdNotFoundException | JsonProcessingException | RequiredValuesMissingException e) {
            HttpStatus status = switch (e.getClass().getSimpleName()) {
                case "IncorrectPasswordException" -> HttpStatus.UNAUTHORIZED;
                case "UserIdNotFoundException"    -> HttpStatus.NOT_FOUND;
                default -> HttpStatus.BAD_REQUEST;
            };
            throw new ResponseStatusException(status, e.getMessage(), e);
        }
    }

}
