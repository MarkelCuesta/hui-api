package com.grupo5.huiapi.entities.user;

import Exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public String insertUser(User user) throws EmailTakenException, UsernameTakenException {
        Optional<User> userOptionalByMail= userRepository.findUserByEmail(user.getEmail());
        if(userOptionalByMail.isPresent())
            throw new EmailTakenException();

        Optional<User> userOptionalByUsername = userRepository.findUserByUsername(user.getUsername());
        if(userOptionalByUsername.isPresent())
            throw new UsernameTakenException();

        userRepository.save(user);
        return "User successfully registered";
    }

    public String deleteUser(Long id, String password) throws UserIdNotFoundException, IncorrectPasswordException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
            throw new UserIdNotFoundException();

        User user = optionalUser.get();
        System.out.println(user.getPassword());
        if(!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException();
        }

        userRepository.delete(user);
        return "User successfully deleted";
    }


    public String updateUser(Long id,String password, User updatingUser) throws UserIdNotFoundException, IncorrectPasswordException, RequiredValuesMissingException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
            throw new UserIdNotFoundException();

        User originalUser = optionalUser.get();

        if( !originalUser.getPassword().equals(password) ) {
            throw new IncorrectPasswordException();
        }

        // Comprobamos si tiene todos los campos obligatorios
        if(updatingUser.getEmail().isEmpty()
                || updatingUser.getUsername().isEmpty()
                || updatingUser.getPassword().isEmpty()
                || updatingUser.getNombre_apellidos().isEmpty())
            throw new RequiredValuesMissingException();

        updatingUser.setId(id);
        userRepository.save(updatingUser);
        return "User updated";
    }
}
