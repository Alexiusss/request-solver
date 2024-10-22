package com.example.request_solver.web;

import com.example.request_solver.model.User;
import com.example.request_solver.service.UserService;
import com.example.request_solver.to.UserTo;
import com.example.request_solver.util.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    public static final String REST_URL = "/api/v1/users";

    private final UserService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserTo>> getAllUsers() {
        List<User> users = service.getAll();
        List<UserTo> userTos = UserUtil.asTos(users);
        return ResponseEntity.ok(userTos);
    }

    @GetMapping("/{name}/name")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserTo> getByName(@PathVariable String name) {
        User user = service.getByName(name);
        UserTo userTo = UserUtil.asTo(user);
        return ResponseEntity.ok(userTo);
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setOperatorRole(@PathVariable Integer id) {
        service.setOperatorRole(id);
    }
}