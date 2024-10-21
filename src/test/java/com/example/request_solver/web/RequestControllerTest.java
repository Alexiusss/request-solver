package com.example.request_solver.web;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RequestControllerTest extends AbstractControllerTest {

    public static final String REST_URL = RequestController.REST_URL;

    @Test
    @WithMockUser
    void create() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getAllByUserId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/100000" + "/userId"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + "/100000"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void submit() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + "/100000" + "/submit"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"OPERATOR"})
    void getAllByUserNameOnConsideration() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/User222" + "/userName"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"OPERATOR"})
    void acceptOrReject() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + "/100000" + "/status/true"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}