package com.grupo5.huiapi.modules.user.service;

import com.grupo5.huiapi.exceptions.*;
import com.grupo5.huiapi.modules.event.entity.Event;
import com.grupo5.huiapi.modules.user.entity.User;

import java.util.List;

public interface UserService {
    User getUser(Long id) throws EntityNotFoundException;
    List<User> getUsers();
    String insertUser(User user) throws EmailTakenException, UsernameTakenException, RequiredValuesMissingException, EntityNotFoundException;
    String updateUser(Long id, String password, User updatingUser) throws IncorrectPasswordException, RequiredValuesMissingException, EntityNotFoundException;
    String deleteUser(Long id, String password) throws IncorrectPasswordException, EntityNotFoundException;
    void enrollToEvent(Event event, User user);
}
