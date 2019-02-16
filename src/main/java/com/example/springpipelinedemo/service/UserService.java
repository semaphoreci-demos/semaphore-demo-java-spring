package com.example.springpipelinedemo.service;

import com.example.springpipelinedemo.dto.UserDto;
import com.example.springpipelinedemo.dto.mapper.UserMapper;
import com.example.springpipelinedemo.model.User;
import com.example.springpipelinedemo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

/**
 * Created by Rimantas Jacikevičius on 19.2.12.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User '%s' was not found", username)));
    }

    public UserDto createUser(String email, String password) {
        notNull(email, "User must have an email");
        notNull(password, "User must have a password");

        User user = userRepository.save(new User(email, passwordEncoder.encode(password)));
        return userMapper.map(user);
    }

}
