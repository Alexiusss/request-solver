package com.example.request_solver.util;

import com.example.request_solver.model.Status;
import com.example.request_solver.to.RequestTo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.experimental.UtilityClass;
import java.time.LocalDate;
import java.util.List;

import static com.example.request_solver.util.RequestUtil.formatString;

@UtilityClass
public class RequestTestData {
    public static final Integer FIRST_REQUEST_ID = 11;
    public static final Integer SECOND_REQUEST_ID = 12;
    public static final RequestTo FIRST_REQUEST = new RequestTo(FIRST_REQUEST_ID, LocalDate.now(), "First request title", "First request description", Status.DRAFT);
    public static final RequestTo SECOND_REQUEST = new RequestTo(SECOND_REQUEST_ID, LocalDate.now(), "Second request title", "Second request description", Status.SENT);
    public static final RequestTo THIRD_REQUEST = new RequestTo(13, LocalDate.now(), "Third request title", "Third request description", Status.ACCEPTED);
    public static final RequestTo FOURTH_REQUEST = new RequestTo(14, LocalDate.now(), "Fourth request title", "Fourth request description", Status.REJECTED);
    public static final List<RequestTo> USER_REQUESTS = List.of(FIRST_REQUEST, SECOND_REQUEST, THIRD_REQUEST, FOURTH_REQUEST);
    public static final RequestTo NEW_REQUEST = new RequestTo(null, LocalDate.now(), "New request", "New request description", Status.DRAFT);
    public static final RequestTo UPDATED_FIRST_REQUEST = new RequestTo(FIRST_REQUEST_ID, LocalDate.now(), "Updated request", "Updated request description", Status.DRAFT);
    public static final RequestTo FORMATTED_SECOND_REQUEST = new RequestTo(SECOND_REQUEST_ID, LocalDate.now(), formatString(SECOND_REQUEST.getTitle()), formatString(SECOND_REQUEST.getDescription()), Status.SENT);

    public static <T> T asParsedJson(ObjectMapper objectMapper, Object obj) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(obj);
        return JsonPath.read(json, "$");
    }
}