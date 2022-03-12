package com.grupo5.huiapi.modules.user.modules.role.service;

import com.grupo5.huiapi.exceptions.EntityNotFoundException;
import com.grupo5.huiapi.modules.user.entity.User;

public interface Roles {
    String addRoleToUser(String roleName, User user) throws EntityNotFoundException;
}
