package com.grupo5.huiapi.entities.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            User markel = new User(
                    "markelca",
                    "password123",
                    "cuestaarribas.markel@gmail.com",
                    "Markel Cuesta"
            );
            User jon = new User(
              "jon33",
              "anotherpassword123",
              "jon@email.com",
              "Jon Arroita"
            );

            repository.saveAll( List.of(markel, jon) );
        };
    }
}
