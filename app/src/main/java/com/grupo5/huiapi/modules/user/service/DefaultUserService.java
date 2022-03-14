package com.grupo5.huiapi.modules.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grupo5.huiapi.exceptions.*;
import com.grupo5.huiapi.modules.EntityType;
import com.grupo5.huiapi.modules.event.entity.Event;
import com.grupo5.huiapi.modules.user.entity.User;
import com.grupo5.huiapi.modules.user.modules.role.entity.Role;
import com.grupo5.huiapi.modules.user.modules.role.service.Roles;
import com.grupo5.huiapi.modules.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
@Qualifier("DefaultUserService")
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final Roles roleService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(UserRepository userRepository, @Qualifier("RolesService") Roles roleService, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if(optionalUser.isEmpty()) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found: {}", username);
        }

        User user = optionalUser.get();
        Role userRole = user.getRole();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority( userRole.getName() ));

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            authorities
        );
    }

    public User get(Long id) throws EntityNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new EntityNotFoundException(EntityType.USER);
        return user.get();
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }
    public String insert(JsonNode jsonUser) throws EmailTakenException, UsernameTakenException, RequiredValuesMissingException, EntityNotFoundException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.treeToValue(jsonUser, User.class);

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
        String encodedPassword = passwordEncoder.encode( user.getPassword() );
        user.setPassword(encodedPassword);
        roleService.addRoleToUser(enteredRole, user);

        userRepository.save(user);

        log.info("User successfully registered");
        return "User successfully registered";
    }
    public String delete(Long id, String password) throws IncorrectPasswordException, EntityNotFoundException {
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
    public String update(Long id, String password, JsonNode updatingJsonUser) throws IncorrectPasswordException, RequiredValuesMissingException, EntityNotFoundException, JsonProcessingException {
        User updatingUser = objectMapper.treeToValue(updatingJsonUser, User.class);

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
    public void enrollToEvent(Event event, User user) {
        user.getEnrolled_events().add(event);
        userRepository.save(user);
    }

}
