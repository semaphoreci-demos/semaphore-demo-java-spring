package com.example.springpipelinedemo.repository;

import com.example.springpipelinedemo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


/**
 * Created by Rimantas Jacikevičius on 19.2.12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = "testContext=true")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void save() {

        Date timestamp = new Date();

        User user = new User("email", "pass");
        User save = userRepository.save(user);

        assertThat(save.getId()).isNotNull();

        Optional<User> result = userRepository.findById(save.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getEmail()).isEqualTo(user.getEmail());
        assertThat(result.get().getPassword()).isEqualTo(user.getPassword());
        assertThat(result.get().getId()).isEqualTo(save.getId());
        assertThat(result.get().getCreatedDate()).isCloseTo(timestamp, 1000);
        assertThat(result.get().getModifiedDate()).isCloseTo(timestamp, 1000);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void save_duplicateKeys() {
        String email = "email";

        try {
            userRepository.save(new User(email, "pass1"));
        } catch (DataIntegrityViolationException e) {
            fail("Not expected at this point");
        }

        userRepository.save(new User(email, "pass2"));
    }


    @Test
    public void findByEmail() {
        String email = "email";

        User user = userRepository.save(new User(email, "pass1"));

        Optional<User> result = userRepository.findByEmail(email);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualToIgnoringGivenFields(user, "createdDate", "modifiedDate");
    }

    @Test
    public void findByEmail_notFound() {
        Optional<User> result = userRepository.findByEmail("email");
        assertThat(result).isEmpty();
    }

}