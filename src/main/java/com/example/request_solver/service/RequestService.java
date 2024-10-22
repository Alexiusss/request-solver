package com.example.request_solver.service;

import com.example.request_solver.AuthUser;
import com.example.request_solver.model.Request;
import com.example.request_solver.model.Status;
import com.example.request_solver.to.RequestTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {
    Page<Request> getAllPaginatedByUserId(int userId, Pageable pageable);

    Request create(RequestTo requestTo, AuthUser authUser);

    void update(RequestTo requestTo, int id);

    void submit(Integer id);

    Page<Request> getAllUnconsideredPageable(Pageable pageable);

    Page<Request> getAllUnconsideredPageableByUsername(String userName, Pageable pageable);

    void setStatus(Integer id, Status status);
}