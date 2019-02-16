package com.example.springpipelinedemo.service;

import com.example.springpipelinedemo.dto.UserDto;
import com.example.springpipelinedemo.model.User;
import com.example.springpipelinedemo.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Rimantas Jacikevičius on 19.2.14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@TestPropertySource(properties = "testContext=true")
public class UserServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;

    @Test
    public void findByName() {
        String name = "name";

        User user = mock(User.class);
        doReturn(Optional.of(user)).when(userRepository).findByEmail(name);

        UserDetails result = userService.loadUserByUsername(name);

        assertThat(user).isEqualTo(result);

        verify(userRepository).findByEmail(name);
        verifyNoMoreInteractions(userRepository);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void findByNameNotFound() {

        doReturn(Optional.empty()).when(userRepository).findByEmail(any());

        userService.loadUserByUsername("name");
    }

    @Test
    public void createUser() {
        String email = "email";
        String password = "pass";

        User user = mock(User.class);
        doReturn(email).when(user).getEmail();
        doReturn(email).when(user).getUsername();
        doReturn(user).when(userRepository).save(any());

        UserDto result = userService.createUser(email, password);

        assertThat(result.username).isEqualTo(email);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(captor.capture());
        verifyNoMoreInteractions(userRepository);

        User captured = captor.getValue();
        assertThat(captured.getEmail()).isEqualTo(email);
        assertThat(encoder.matches(password,captured.getPassword())).isTrue();

    }

}