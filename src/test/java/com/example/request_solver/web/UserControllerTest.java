package com.example.request_solver.web;

import com.example.request_solver.model.Role;
import com.example.request_solver.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.request_solver.util.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerTest {

    public static final String REST_URL = UserController.REST_URL;

    @Autowired
    private UserService userService;

    @Test
    @WithUserDetails(value = "admin")
    void getAllUsers() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(USER_LIST)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "admin")
    void getByName() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/" + USER_NAME + "/name"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(USER_TO)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "admin")
    void setOperatorRole() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + "/" + USER_ID + "/role")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        boolean isRoleAdded = userService.get(USER_ID).getRoles().contains(Role.OPERATOR);
        Assertions.assertThat(isRoleAdded).isTrue();
    }
}