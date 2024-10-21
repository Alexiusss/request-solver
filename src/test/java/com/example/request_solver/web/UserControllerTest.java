package com.example.request_solver.web;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerTest {

    public static final String REST_URL = UserController.REST_URL;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAllUsers() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getByName() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/test" + "/name"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void setOperatorRole() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + "/1000000" + "/role"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}