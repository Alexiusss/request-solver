package com.example.request_solver.util;

import com.example.request_solver.model.Status;
import com.example.request_solver.to.RequestTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.experimental.UtilityClass;


import java.time.LocalDate;
import java.util.List;

@UtilityClass
public class RequestTestData {
    public static final RequestTo FIRST_REQUEST = new RequestTo(11, LocalDate.now(), "First request title", "First request description", Status.DRAFT);
    public static final RequestTo SECOND_REQUEST = new RequestTo(12, LocalDate.now(), "Second request title", "Second request description", Status.SENT);
    public static final RequestTo THIRD_REQUEST = new RequestTo(13, LocalDate.now(), "Third request title", "Third request description", Status.ACCEPTED);
    public static final RequestTo FOURTH_REQUEST = new RequestTo(14, LocalDate.now(), "Fourth request title", "Fourth request description", Status.REJECTED);

    public static final List<RequestTo> USER_REQUESTS = List.of(FIRST_REQUEST, SECOND_REQUEST, THIRD_REQUEST, FOURTH_REQUEST);

    public static final RequestTo NEW_REQUEST = new RequestTo(null, LocalDate.now(), "New request", "New request", Status.DRAFT);

    public static <T> T asParsedJson(ObjectMapper objectMapper, Object obj) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(obj);
        return JsonPath.read(json, "$");
    }
}