package com.grupo5.huiapi.modules.user.service;

import com.grupo5.huiapi.modules.EntityType;
import com.grupo5.huiapi.exceptions.*;
import com.grupo5.huiapi.modules.user.entity.User;
import com.grupo5.huiapi.modules.user.modules.role.service.RoleService;
import com.grupo5.huiapi.modules.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new EntityNotFoundException(EntityType.USER);
        return user.get();
    }

    public String insertUser(User user) throws EmailTakenException, UsernameTakenException, RequiredValuesMissingException, EntityNotFoundException {
        Optional<User> userOptionalByMail= userRepository.findUserByEmail(user.getEmail());
        if(userOptionalByMail.isPresent())
            throw new EmailTakenException();

        Optional<User> userOptionalByUsername = userRepository.findUserByUsername(user.getUsername());
        if(userOptionalByUsername.isPresent())
            throw new UsernameTakenException();

        String missingValues = user.checkNullFields();
        if(missingValues != null)
            throw new RequiredValuesMissingException(missingValues);
        System.out.println(user);

        if (user.getRole().getName() == null)
            roleService.addRoleToUser("user", user);

        String enteredRole = user.getRole().getName();

        String addingRole = enteredRole == null ? "user" : enteredRole;
         roleService.addRoleToUser(enteredRole, user);

        userRepository.save(user);

        log.info("User successfully registered");
        return "User successfully registered";
    }

    public String deleteUser(Long id, String password) throws IncorrectPasswordException, EntityNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
            throw new EntityNotFoundException(EntityType.USER);

        User user = optionalUser.get();
        if(!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException();
        }

        userRepository.delete(user);
        return "User successfully deleted";
    }


    public String updateUser(Long id,String password, User updatingUser) throws IncorrectPasswordException, RequiredValuesMissingException, EntityNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
            throw new EntityNotFoundException(EntityType.USER);

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


    public void save(User user) {
        userRepository.save(user);
    }
}
