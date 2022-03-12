package com.grupo5.huiapi.modules.user.modules.role.service;

import com.grupo5.huiapi.exceptions.EntityNotFoundException;
import com.grupo5.huiapi.modules.EntityType;
import com.grupo5.huiapi.modules.user.entity.User;
import com.grupo5.huiapi.modules.user.modules.role.entity.Role;
import com.grupo5.huiapi.modules.user.modules.role.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service @Transactional @Slf4j
public class RoleService  {
    public final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role saveRole(Role role) {
        this.roleRepository.save(role);
        return null;
    }
    public String addRoleToUser(String roleName, User user) throws EntityNotFoundException {
        Optional<Role> optionalRole = roleRepository.findByName(roleName);
        if(optionalRole.isEmpty())
            throw new EntityNotFoundException(EntityType.ROLE);

        user.setRole( optionalRole.get() );
        return null;
    }
}
