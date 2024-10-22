package com.example.request_solver.util;

import com.example.request_solver.model.User;
import com.example.request_solver.to.UserTo;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {
    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static UserTo asTo(User user) {
        return new UserTo(user.id(), user.getCreatedAt().toLocalDate(), user.getUsername(), user.getRoles());
    }

    public static List<UserTo> asTos(List<User> users) {
        return users.stream()
                .map(UserUtil::asTo)
                .collect(Collectors.toList());
    }
}