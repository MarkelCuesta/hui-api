package com.grupo5.huiapi.user;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public List<User> getUsers() {
        User markel = new User();
        markel.setUsername("markelca");
        markel.setNombre_apellidos("Markel Cuesta");
        markel.setEmail("cuestaarribas.markel@gmail.com");

        User jon = new User();
        jon.setUsername("jonusername");
        jon.setNombre_apellidos("Jon Arroita");
        jon.setEmail("jon@email.com");
        return List.of(markel, jon);
    }
}
