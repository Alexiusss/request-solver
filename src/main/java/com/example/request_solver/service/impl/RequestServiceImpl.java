package com.example.request_solver.service.impl;

import com.example.request_solver.AuthUser;
import com.example.request_solver.exception.IllegalRequestData;
import com.example.request_solver.model.Request;
import com.example.request_solver.model.Status;
import com.example.request_solver.model.User;
import com.example.request_solver.repository.RequestRepository;
import com.example.request_solver.repository.UserRepository;
import com.example.request_solver.service.RequestService;
import com.example.request_solver.to.RequestTo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Request> getAllPaginatedByUserId(int userId, Pageable pageable) {
        return requestRepository.getAllByUserId(userId, pageable);
    }

    @Override
    @Transactional
    public Request create(RequestTo requestTo, AuthUser authUser) {
        Assert.notNull(requestTo, "request must not be null");
        if (!requestTo.isNew()) {
            throw new IllegalRequestData("Request id must be empty");
        }
        User user = userRepository.getReferenceById(authUser.getId());
        Request request = new Request(requestTo.getTitle(), requestTo.getDescription(), requestTo.getStatus(), user);
        request.setStatus(Status.DRAFT);
        return requestRepository.saveAndFlush(request);
    }

    @Override
    @Transactional
    public void update(RequestTo requestTo, int id) {
        Assert.notNull(requestTo, "request must not be null");
        if (!requestTo.getStatus().equals(Status.DRAFT)) {
            throw new IllegalRequestData("The status of the request must be DRAFT for editing");
        }
        Request request = requestRepository.getExisted(id);
        request.setTitle(requestTo.getTitle());
        request.setDescription(requestTo.getDescription());
    }

    @Override
    @Transactional
    public void submit(Integer id) {
        Request request = requestRepository.getExisted(id);
        if (!request.getStatus().equals(Status.DRAFT)) {
            throw new IllegalRequestData("Requests can only be submitted with the DRAFT status");
        }
        request.setStatus(Status.SENT);
    }

    @Override
    public Page<Request> getAllUnconsideredPageable(Pageable pageable) {
        return requestRepository.getAllByStatus(Status.SENT, pageable);
    }

    @Override
    public Page<Request> getAllUnconsideredPageableByUsername(String userName, Pageable pageable) {
        return requestRepository.getAllByStatusAndUserUsernameContaining(Status.SENT, userName, pageable);
    }

    @Override
    @Transactional
    public void setStatus(Integer id, Status status) {
        Request request = requestRepository.getExisted(id);
        if (!request.getStatus().equals(Status.SENT)) {
            throw new IllegalRequestData("The status of the request must be SENT for accept or reject");
        }
        request.setStatus(status);
    }
}