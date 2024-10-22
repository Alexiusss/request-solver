package com.example.request_solver.to;

import com.example.request_solver.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class UserTo {
    Integer id;
    LocalDate createdAt;
    String username;
    List<Role> roles;
}