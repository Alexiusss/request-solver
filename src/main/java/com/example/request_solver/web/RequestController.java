package com.example.request_solver.web;

import com.example.request_solver.AuthUser;
import com.example.request_solver.model.Request;
import com.example.request_solver.model.Status;
import com.example.request_solver.service.RequestService;
import com.example.request_solver.to.RequestCreationTo;
import com.example.request_solver.to.RequestTo;
import com.example.request_solver.util.RequestUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
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

import static com.example.request_solver.util.RequestUtil.asTo;
import static com.example.request_solver.util.RequestUtil.createPageable;

@RestController
@AllArgsConstructor
@SecurityRequirement(name = "baseauth")
@RequestMapping(path = RequestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RequestController {

    public static final String REST_URL = "/api/v1/requests";

    private final RequestService service;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return a list of requests for the auth user", description = "Base auth is required to access this API")
    public Page<RequestTo> getAllForAuthUser(@Schema(hidden = true) @AuthenticationPrincipal AuthUser authUser,
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
    @Operation(summary = "Create a new request from an auth user", description = "Base auth is required to access this API")
    public RequestTo create(@Schema(hidden = true) @AuthenticationPrincipal AuthUser authUser,
                            @Valid @RequestBody RequestCreationTo creationTo) {
        return asTo(service.create(asTo(creationTo), authUser));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update a user's request using their id", description = "Base auth is required to access this API")
    public void update(@RequestBody RequestTo requestTo, @PathVariable Integer id) {
        service.update(requestTo, id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping("/{id}/submit")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Send the user's request to the consideration", description = "Base auth is required to access this API")
    public void submit(@PathVariable Integer id) {
        service.submit(id);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @GetMapping("/unconsidered")
    @Operation(summary = "Get all pending queries", description = "Base auth is required to access this API")
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
    @Operation(summary = "Get all pending queries by username", description = "Base auth is required to access this API")
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
    @Operation(summary = "Accept the pending request using its it id", description = "Base auth is required to access this API")
    public void accept(@PathVariable Integer id) {
        service.setStatus(id, Status.ACCEPTED);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @PatchMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Reject the pending request using its it id", description = "Base auth is required to access this API")
    public void reject(@PathVariable Integer id) {
        service.setStatus(id, Status.REJECTED);
    }
}