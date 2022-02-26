package com.grupo5.huiapi.config;

import com.grupo5.huiapi.entities.category.Category;
import com.grupo5.huiapi.entities.category.CategoryRepository;
import com.grupo5.huiapi.entities.user.User;
import com.grupo5.huiapi.entities.user.UserRepository;
import com.grupo5.huiapi.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import static com.grupo5.huiapi.utils.ListUtils.concat;

@Configuration
public class DBConfig {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            userRepository.saveAll( getInitialUsers() );
            categoryRepository.saveAll( getInitialCategories() );

        };
    }

    private List<User> getInitialUsers() {
        return List.of(
            new User(
                "jon33",
                "anotherpassword123",
                "jon@email.com",
                "Jon Arroita"
            ),
            new User(
                "markelca",
                "password123",
                "cuestaarribas.markel@gmail.com",
                "Markel Cuesta"
            )

        );
    }

    private List<Category> getInitialCategories() {
        List<Category> ret = new ArrayList<>();
        // Principales
        List<Category> principales = Category.createCategories("Deportes", "Música", "Baile", "Fotografía");
        ret.addAll(principales);
        // Deportes
        Category deportes = ListUtils.getCategoryByName(principales, "Deportes");
        List<Category> deportesSubcategories = deportes.addSubCategories(
                "Fútbol",
                "Baloncesto",
                "Artes marciales",
                "Tenis",
                "Street",
                "Running",
                "Gym",
                "Calistenia",
                "Crossfit",
                "Padel"
        );
        ret.addAll(deportesSubcategories);
            // Artes Marciales
            Category artesMarciales = ListUtils.getCategoryByName(deportesSubcategories, "Artes marciales");
            List<Category> artesMarcialesSubCategories = artesMarciales.addSubCategories(
                "Karate", "MMA", "Jujutsu", "Taekwondo", "Boxeo", "Muay thai"
            );
            ret.addAll(artesMarcialesSubCategories);
            // Street
            Category street = ListUtils.getCategoryByName(deportesSubcategories, "Street") ;
            List<Category> streetSubCategories = street.addSubCategories("Parkour", "Skate");
            ret.addAll(streetSubCategories);

            return ret;
    }



}
