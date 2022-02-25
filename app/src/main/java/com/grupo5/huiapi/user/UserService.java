package com.grupo5.huiapi.user;

import Exceptions.EmailTakenException;
import Exceptions.IncorrectPasswordException;
import Exceptions.UserIdNotFoundException;
import Exceptions.UsernameTakenException;
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
        if(!optionalUser.isPresent())
            throw new UserIdNotFoundException();

        User user = optionalUser.get();
        System.out.println(user.getPassword());
        if(!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException();
        }

        userRepository.delete(user);
        return "User successfully deleted";
    }
}
