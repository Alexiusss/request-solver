package com.example.request_solver.util;

import com.example.request_solver.model.Role;
import com.example.request_solver.model.User;
import com.example.request_solver.to.UserTo;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class UserTestData {
    public static final Integer USER_ID = 3;
    public static final String USER_NAME = "user";
    public static final UserTo ADMIN_TO = new UserTo(1, LocalDate.now(), "admin", List.of(Role.ADMIN));
    public static final UserTo OPERATOR_TO = new UserTo(2, LocalDate.now(), "operator", List.of(Role.OPERATOR));
    public static final UserTo USER_TO = new UserTo(USER_ID, LocalDate.now(), USER_NAME, List.of(Role.USER));
    public static final List<UserTo> USER_LIST = List.of(ADMIN_TO, OPERATOR_TO, USER_TO);

    public static User USER = new User(USER_NAME, "userpassword", List.of(Role.USER));
}