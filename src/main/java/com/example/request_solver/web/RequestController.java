package com.example.request_solver.web;

import com.example.request_solver.AuthUser;
import com.example.request_solver.model.Request;
import com.example.request_solver.model.Status;
import com.example.request_solver.service.RequestService;
import com.example.request_solver.to.RequestTo;
import com.example.request_solver.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.request_solver.util.RequestUtil.createPageable;

@RestController
@RequestMapping(path = RequestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestController {

    public static final String REST_URL = "/api/v1/requests";

    @Autowired
    private RequestService service;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public Page<RequestTo> getAllForAuthUser(@AuthenticationPrincipal AuthUser authUser,
                                             @RequestParam(required = false, defaultValue = "0") Integer page,
                                             @RequestParam(required = false, defaultValue = "5") Integer size,
                                             @RequestParam(defaultValue = "ASC") String direction
    ) {
        Pageable pageable = createPageable(page, size, direction);
        Page<Request> requestPage = service.getAllPaginatedByUserId(authUser.getId(), pageable);
        List<RequestTo> requestTos = RequestUtil.asTos(requestPage.getContent());
        return new PageImpl<>(requestTos, pageable, requestPage.getTotalElements());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestTo create(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody RequestTo requestTo) {
        return RequestUtil.asTo(service.create(requestTo, authUser));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody RequestTo requestTo, @PathVariable Integer id) {
        service.update(requestTo, id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping("/{id}/submit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void submit(@PathVariable Integer id) {
        service.submit(id);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @GetMapping("/unconsidered")
    public Page<RequestTo> getAllUnconsidered(@RequestParam(required = false, defaultValue = "0") Integer page,
                                              @RequestParam(required = false, defaultValue = "5") Integer size,
                                              @RequestParam(defaultValue = "ASC") String direction) {
        Pageable pageable = createPageable(page, size, direction);
        Page<Request> requestPage = service.getAllUnconsideredPageable(pageable);
        List<RequestTo> requestTos = RequestUtil.asTosForOperator(requestPage.getContent());
        return new PageImpl<>(requestTos, pageable, requestPage.getTotalElements());
    }


    @PreAuthorize("hasAuthority('OPERATOR')")
    @GetMapping("/unconsidered/{userName}/username")
    public Page<RequestTo> getAllUnconsideredByUsername(@PathVariable String userName,
                                               @RequestParam(required = false, defaultValue = "0") Integer page,
                                               @RequestParam(required = false, defaultValue = "5") Integer size,
                                               @RequestParam(defaultValue = "ASC") String direction) {
        Pageable pageable = createPageable(page, size, direction);
        Page<Request> requestPage = service.getAllUnconsideredPageableByUsername(userName, pageable);
        List<RequestTo> requestTos = RequestUtil.asTosForOperator(requestPage.getContent());
        return new PageImpl<>(requestTos, pageable, requestPage.getTotalElements());
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @PatchMapping("/{id}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void accept(@PathVariable Integer id) {
        service.setStatus(id, Status.ACCEPTED);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @PatchMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reject(@PathVariable Integer id) {
        service.setStatus(id, Status.REJECTED);
    }
}