package com.example.request_solver.web;

import com.example.request_solver.model.Status;
import com.example.request_solver.repository.RequestRepository;
import com.example.request_solver.to.RequestTo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.example.request_solver.util.RequestTestData.*;
import static com.example.request_solver.util.RequestUtil.asTo;
import static com.example.request_solver.util.UserTestData.USER;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Request controller test")
class RequestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = RequestController.REST_URL;

    @Autowired
    private RequestRepository repository;

    @Test
    @WithUserDetails
    @DisplayName("Get all requests for authenticated user, expected success")
    void getAllForAuthUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/user")
                .param("direction", "DESC"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", equalTo(asParsedJson(objectMapper, USER_REQUESTS))));
    }

    @Test
    @WithUserDetails
    @DisplayName("Create a new request from an authenticated user, expected success")
    void create() throws Exception {
        MvcResult result = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(NEW_REQUEST)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();


        RequestTo saved = objectMapper.readValue(result.getResponse().getContentAsString(), RequestTo.class);
        NEW_REQUEST.setId(saved.getId());

        Assertions.assertThat(saved)
                .usingRecursiveComparison()
                .isEqualTo(NEW_REQUEST);
    }

    @Test
    @WithUserDetails
    @DisplayName("Update a user's request, expected success")
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + FIRST_REQUEST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UPDATED_FIRST_REQUEST)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RequestTo requestFromDB = asTo(repository.getExisted(FIRST_REQUEST_ID));
        Assertions.assertThat(requestFromDB)
                .usingRecursiveComparison()
                .isEqualTo(UPDATED_FIRST_REQUEST);
    }

    @Test
    @WithUserDetails
    @DisplayName("Submit the user's request, expected success")
    void submit() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + "/" + FIRST_REQUEST_ID + "/submit"))
                .andExpect(status().isNoContent());

        RequestTo requestFromDB = asTo(repository.getExisted(FIRST_REQUEST_ID));
        Assertions.assertThat(requestFromDB.getStatus())
                .isEqualTo(Status.SENT);
    }

    @Test
    @WithUserDetails(value = "operator")
    @DisplayName("Get all pending queries, expected success")
    void getAllUnconsidered() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/unconsidered"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", equalTo(asParsedJson(objectMapper, List.of(FORMATTED_SECOND_REQUEST)))));
    }

    @Test
    @WithUserDetails(value = "operator")
    @DisplayName("Get all pending queries by username, expected success")
    void getAllUnconsideredByUsername() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/unconsidered/" + USER.getUsername() + "/username")
                .param("direction", "DESC"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", equalTo(asParsedJson(objectMapper, List.of(FORMATTED_SECOND_REQUEST)))));
    }

    @Test
    @WithUserDetails(value = "operator")
    @DisplayName("Accept the pending request, expected success")
    void accept() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + "/" + SECOND_REQUEST_ID + "/accept"))
                .andExpect(status().isNoContent());

        RequestTo requestFromDB = asTo(repository.getExisted(SECOND_REQUEST_ID));
        Assertions.assertThat(requestFromDB.getStatus())
                .isEqualTo(Status.ACCEPTED);
    }

    @Test
    @WithUserDetails(value = "operator")
    @DisplayName("Reject the pending request, expected success")
    void reject() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + "/" + SECOND_REQUEST_ID + "/reject"))
                .andExpect(status().isNoContent());

        RequestTo requestFromDB = asTo(repository.getExisted(SECOND_REQUEST_ID));
        Assertions.assertThat(requestFromDB.getStatus())
                .isEqualTo(Status.REJECTED);
    }
}