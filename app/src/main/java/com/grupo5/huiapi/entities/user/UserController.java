package com.grupo5.huiapi.entities.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.grupo5.huiapi.exceptions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "{id}")
    public User getUser(@PathVariable("id") Long id) {
        try {
            return userService.getUser(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping
    public String registerNewUser(@RequestBody User user) {
        try {
            return userService.insertUser(user);
        } catch (EmailTakenException | UsernameTakenException | RequiredValuesMissingException e) {
            HttpStatus status = e instanceof RequiredValuesMissingException ? HttpStatus.BAD_REQUEST : HttpStatus.CONFLICT;
             throw new ResponseStatusException(status, e.getMessage(), e);
        }
    }
    @DeleteMapping(path = "{id}")
    public String deleteUser(@PathVariable("id") Long id, @RequestBody ObjectNode body) {
        String password = body.get("password").asText();
        try {
            return userService.deleteUser(id, password);
        } catch (IncorrectPasswordException | EntityNotFoundException e) {
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
        } catch (IncorrectPasswordException | JsonProcessingException | RequiredValuesMissingException | EntityNotFoundException e) {
            HttpStatus status = switch (e.getClass().getSimpleName()) {
                case "IncorrectPasswordException" -> HttpStatus.UNAUTHORIZED;
                case "EntityNotFoundException"    -> HttpStatus.NOT_FOUND;
                default -> HttpStatus.BAD_REQUEST;
            };
            throw new ResponseStatusException(status, e.getMessage(), e);
        }
    }



}
