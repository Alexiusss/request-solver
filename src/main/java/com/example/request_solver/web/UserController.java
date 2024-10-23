package com.example.request_solver.web;

import com.example.request_solver.model.User;
import com.example.request_solver.service.UserService;
import com.example.request_solver.to.UserTo;
import com.example.request_solver.util.UserUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "baseauth")
@RequestMapping(path = UserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    public static final String REST_URL = "/api/v1/users";

    private final UserService service;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Return a list of users", description = "Base auth is required to access this API")
    public ResponseEntity<List<UserTo>> getAllUsers() {
        List<User> users = service.getAll();
        List<UserTo> userTos = UserUtil.asTos(users);
        return ResponseEntity.ok(userTos);
    }

    @GetMapping("/{name}/name")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Get a user by its name", description = "Base auth is required to access this API")
    public ResponseEntity<UserTo> getByName(@PathVariable String name) {
        User user = service.getByName(name);
        UserTo userTo = UserUtil.asTo(user);
        return ResponseEntity.ok(userTo);
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Assign the operator role to the user", description = "Base auth is required to access this API")
    public void setOperatorRole(@PathVariable Integer id) {
        service.setOperatorRole(id);
    }
}