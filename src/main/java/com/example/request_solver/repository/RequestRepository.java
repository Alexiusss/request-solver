package com.example.request_solver.repository;

import com.example.request_solver.exception.NotFoundException;
import com.example.request_solver.model.Request;
import com.example.request_solver.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static com.example.request_solver.util.ValidationUtil.checkNotFoundWithId;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    Page<Request> getAllByUserId(int userId, Pageable pageable);

    Page<Request> getAllByStatus(Status status, Pageable pageable);

    Page<Request> getAllByStatusAndUserUsernameContaining(Status status, String username, Pageable pageable);

    default Request getExisted(int id) {
        return checkNotFoundWithId(findById(id).orElseThrow(() ->
                new NotFoundException("Entity with " + id + " not found")), id);
    }
}