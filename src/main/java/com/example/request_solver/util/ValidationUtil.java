package com.example.request_solver.util;

import com.example.request_solver.exception.NotFoundException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtil {

    public static <T> T checkNotFoundWithId(T entity, int id) {
        if (entity == null) {
            throw new NotFoundException("Entity with " + id + " not found");
        }
        return entity;
    }

    public static <T> T checkNotFoundWithName(T entity, String name) {
        if (entity == null) {
            throw new NotFoundException("User with " + name + " not found");
        }
        return entity;
    }
}