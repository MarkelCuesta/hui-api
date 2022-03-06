package com.grupo5.huiapi.entities.user;

import com.grupo5.huiapi.exceptions.*;
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

    public User getUser(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException();
        return user.get();
    }

    public String insertUser(User user) throws EmailTakenException, UsernameTakenException, RequiredValuesMissingException {
        Optional<User> userOptionalByMail= userRepository.findUserByEmail(user.getEmail());
        if(userOptionalByMail.isPresent())
            throw new EmailTakenException();

        Optional<User> userOptionalByUsername = userRepository.findUserByUsername(user.getUsername());
        if(userOptionalByUsername.isPresent())
            throw new UsernameTakenException();

        String missingValues = user.checkNullFields();
        if(missingValues != null)
            throw new RequiredValuesMissingException(missingValues);

        userRepository.save(user);
        return "User successfully registered";
    }

    public String deleteUser(Long id, String password) throws UserNotFoundException, IncorrectPasswordException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
            throw new UserNotFoundException();

        User user = optionalUser.get();
        if(!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException();
        }

        userRepository.delete(user);
        return "User successfully deleted";
    }


    public String updateUser(Long id,String password, User updatingUser) throws UserNotFoundException, IncorrectPasswordException, RequiredValuesMissingException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
            throw new UserNotFoundException();

        User originalUser = optionalUser.get();

        if( !originalUser.getPassword().equals(password) ) {
            throw new IncorrectPasswordException();
        }

        // Comprobamos si tiene todos los campos obligatorios
        String nullFields = updatingUser.checkNullFields();
        if(nullFields != null)
            throw new RequiredValuesMissingException(nullFields);

        updatingUser.setId(id);
        userRepository.save(updatingUser);
        return "User updated";
    }


}
