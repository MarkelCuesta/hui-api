package com.grupo5.huiapi.exceptions;

import com.grupo5.huiapi.modules.EntityType;
import lombok.Getter;

public class EntityNotFoundException extends CustomException {
    @Getter
    private final EntityType type;
    public EntityNotFoundException(EntityType type) {
        super("Couldn't find the " + type.toString().toLowerCase() + " with this id.");
        this.type = type;
    }
}
