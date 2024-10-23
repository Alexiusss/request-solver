package com.example.request_solver.service;

import com.example.request_solver.AuthUser;
import com.example.request_solver.model.Request;
import com.example.request_solver.model.Status;
import com.example.request_solver.to.RequestTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing {@link Request} entities.
 */
public interface RequestService {

    /**
     * Retrieves all requests for a given user ID, paginated.
     *
     * @param userId   The ID of the user whose requests to retrieve.
     * @param pageable Pagination information.
     * @return A Page of {@link Request} entities.
     */
    Page<Request> getAllPaginatedByUserId(int userId, Pageable pageable);

    /**
     * Creates a new request.
     *
     * @param requestTo The DTO containing the request data.
     * @param authUser  The authenticated user creating the request.
     * @return The created {@link Request} entity.
     */
    Request create(RequestTo requestTo, AuthUser authUser);

    /**
     * Updates an existing request.
     *
     * @param requestTo The DTO containing the updated request data.
     * @param id        The ID of the request to update.
     */
    void update(RequestTo requestTo, int id);

    /**
     * Submits a request.
     *
     * @param id The ID of the request to submit.
     */
    void submit(Integer id);

    /**
     * Retrieves all unconsidered requests, paginated.
     *
     * @param pageable Pagination information.
     * @return A Page of {@link Request} entities.
     */
    Page<Request> getAllUnconsideredPageable(Pageable pageable);


    /**
     * Retrieves all unconsidered requests for a given username, paginated.
     *
     * @param userName The username whose requests to retrieve.
     * @param pageable Pagination information.
     * @return A Page of {@link Request} entities.
     */
    Page<Request> getAllUnconsideredPageableByUsername(String userName, Pageable pageable);

    /**
     * Sets the status of a request.
     *
     * @param id     The ID of the request to update.
     * @param status The new status of the request.
     */
    void setStatus(Integer id, Status status);
}