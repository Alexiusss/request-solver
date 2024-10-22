package com.example.request_solver.service;

import com.example.request_solver.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User get(int id);

    User getByName(String username);

    @Transactional
    void setOperatorRole(int id);
}
