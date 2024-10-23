package com.example.request_solver.service;

import com.example.request_solver.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(int id);

    User getByName(String username);

    void setOperatorRole(int id);
}
