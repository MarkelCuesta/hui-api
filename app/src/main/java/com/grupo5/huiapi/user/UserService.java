package com.grupo5.huiapi.user;

import Exceptions.EmailTakenException;
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


    public String insertUser(User user) throws EmailTakenException {
        Optional<User> userOptional= userRepository.findUserByEmail(user.getEmail());
        if(userOptional.isPresent()) {
            throw new EmailTakenException();
        }
        System.out.println(user);
        return "User successfully registered";
    }
}
