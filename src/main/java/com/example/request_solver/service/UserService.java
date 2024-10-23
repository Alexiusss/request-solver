package com.example.request_solver.service;

import com.example.request_solver.model.User;

import java.util.List;

/**
 * Service interface for managing {@link User} entities.
 */
public interface UserService {

    /**
     * Retrieves all users.
     *
     * @return A list of all {@link User} entities.
     */
    List<User> getAll();

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@link User} entity with the given ID, or null if not found.
     */
    User get(int id);

    /**
     * Retrieves a user by username.
     *
     * @param username The username of the user to retrieve.
     * @return The {@link User} entity with the given username, or null if not found.
     */
    User getByName(String username);

    /**
     * Sets the role of a user to "OPERATOR".
     *
     * @param id The ID of the user to update.
     */
    void setOperatorRole(int id);
}
