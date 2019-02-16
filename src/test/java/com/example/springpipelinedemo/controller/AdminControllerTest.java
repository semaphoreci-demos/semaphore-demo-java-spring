package com.example.springpipelinedemo.controller;

import com.example.springpipelinedemo.SpringPipelineDemoApplication;
import com.example.springpipelinedemo.model.User;
import com.example.springpipelinedemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import java.nio.charset.Charset;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Rimantas Jacikevičius on 19.2.14.
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = SpringPipelineDemoApplication.class)
@TestPropertySource(properties = "testContext=true")
@DirtiesContext
public class AdminControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    PasswordEncoder encoder;
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
    public void home() throws Exception {
        String username = "hello@world.com";
        String pass = "pass123";

        User user = new User(username, encoder.encode(pass));
        doReturn(user).when(userService).loadUserByUsername(username);

        String auth = username+":"+pass;

        String token = "Basic "+ new String(Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII"))));

        mockMvc.perform(
                get("/admin/home")
                        .header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(
                        status().isOk());
    }

    @Test
    public void home_unauthorised() throws Exception {
        mockMvc.perform(
                get("/admin/home"))
                .andExpect(
                        status().isUnauthorized());
    }
}