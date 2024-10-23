package com.example.request_solver.util;

import com.example.request_solver.exception.IllegalRequestData;
import com.example.request_solver.model.Request;
import com.example.request_solver.to.RequestCreationTo;
import com.example.request_solver.to.RequestTo;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RequestUtil {
    public static RequestTo asTo(Request request) {
        return new RequestTo(request.getId(), request.getCreatedAt().toLocalDate(), request.getTitle(), request.getDescription(), request.getStatus());
    }

    public static RequestTo asTo(RequestCreationTo request) {
        return new RequestTo(request.getTitle(), request.getDescription());
    }

    public static List<RequestTo> asTos(List<Request> requests) {
        return requests.stream()
                .map(RequestUtil::asTo)
                .collect(Collectors.toList());
    }

    public static Pageable createPageable(int page, int size, String direction) {
        try {
            return PageRequest.of(page, size, Sort.Direction.valueOf(direction.toUpperCase()), "createdAt");
        } catch (IllegalArgumentException e) {
            throw new IllegalRequestData("Invalid sort direction: " + direction);
        }
    }
}