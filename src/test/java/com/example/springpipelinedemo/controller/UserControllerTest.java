package com.example.springpipelinedemo.controller;

import com.example.springpipelinedemo.SpringPipelineDemoApplication;
import com.example.springpipelinedemo.controller.forms.SignupForm;
import com.example.springpipelinedemo.dto.UserDto;
import com.example.springpipelinedemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Rimantas Jacikevičius on 19.2.14.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SpringPipelineDemoApplication.class)
@TestPropertySource(properties = "testContext=true")
@DirtiesContext
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    public void signup() throws Exception {
        SignupForm form = new SignupForm("a@b.test","somepassword");

        doReturn(new UserDto(form.email)).when(userService).createUser(form.email, form.password);

        mockMvc.perform(
                post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(form)))
                .andExpect(
                       status().isOk())
                .andExpect(
                        jsonPath("$.username", is(form.email))
                );

        verify(userService).createUser(form.email, form.password);
        verifyNoMoreInteractions(userService);
    }

}