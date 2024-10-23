package com.example.request_solver.util;

import com.example.request_solver.exception.NotFoundException;
import com.example.request_solver.model.User;
import lombok.experimental.UtilityClass;

/**
 * Utility class for validation purposes.
 */
@UtilityClass
public class ValidationUtil {

    /**
     * Checks if an entity is found by its ID. If not found, throws a {@link NotFoundException}.
     *
     * @param entity The entity to check.
     * @param id     The ID of the entity.
     * @param <T>    The type of the entity.
     * @return The entity if found.
     * @throws NotFoundException If the entity is not found.
     */
    public static <T> T checkNotFoundWithId(T entity, int id) {
        if (entity == null) {
            throw new NotFoundException("Entity with " + id + " not found");
        }
        return entity;
    }

    /**
     * Checks if an entity (user) is found by its name. If not found, throws a {@link NotFoundException}.
     *
     * @param user The user to check.
     * @param name The name of the entity (user).
     * @return The user if found.
     * @throws NotFoundException If the user  is not found.
     */
    public static User checkNotFoundWithName(User user, String name) {
        if (user == null) {
            throw new NotFoundException("User with " + name + " not found");
        }
        return user;
    }
}