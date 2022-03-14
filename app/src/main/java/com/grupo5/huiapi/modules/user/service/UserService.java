package com.grupo5.huiapi.modules.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.grupo5.huiapi.exceptions.*;
import com.grupo5.huiapi.modules.Service;
import com.grupo5.huiapi.modules.event.entity.Event;
import com.grupo5.huiapi.modules.user.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends Service<User, Long>, UserDetailsService {
    User get(Long id) throws EntityNotFoundException;
    List<User> getAll();
    String insert(JsonNode user) throws EmailTakenException, UsernameTakenException, RequiredValuesMissingException, EntityNotFoundException, JsonProcessingException;
    String update(Long id, String password, JsonNode updatingUser) throws IncorrectPasswordException, RequiredValuesMissingException, EntityNotFoundException, JsonProcessingException;
    String delete(Long id, String password) throws IncorrectPasswordException, EntityNotFoundException;
    void enrollToEvent(Event event, User user);
}
