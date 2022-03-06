package com.grupo5.huiapi.exceptions;

import com.grupo5.huiapi.config.EntityType;
import lombok.Getter;

public class EntityNotFoundException extends Exception {
    @Getter
    private final EntityType type;
    public EntityNotFoundException(EntityType type) {
        super("Couldn't find the " + type.toString().toLowerCase() + " with this id.");
        this.type = type;
    }
}
