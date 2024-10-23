package com.example.request_solver.util;

import com.example.request_solver.model.User;
import com.example.request_solver.to.UserTo;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for working with {@link User} entities.
 */
public class UserUtil {

    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * Converts a {@link User} entity to a {@link UserTo} DTO.
     *
     * @param user The {@link User} entity to convert.
     * @return The corresponding {@link UserTo} DTO.
     */
    public static UserTo asTo(User user) {
        return new UserTo(user.id(), user.getCreatedAt().toLocalDate(), user.getUsername(), user.getRoles());
    }

    /**
     * Converts a list of {@link User} entities to a list of {@link UserTo} DTOs.
     *
     * @param users The list of {@link User} entities to convert.
     * @return The corresponding list of {@link UserTo} DTOs.
     */
    public static List<UserTo> asTos(List<User> users) {
        return users.stream()
                .map(UserUtil::asTo)
                .collect(Collectors.toList());
    }
}