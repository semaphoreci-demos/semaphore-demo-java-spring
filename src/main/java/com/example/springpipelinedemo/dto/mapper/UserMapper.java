package com.example.springpipelinedemo.dto.mapper;

import com.example.springpipelinedemo.dto.UserDto;
import com.example.springpipelinedemo.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by Rimantas Jacikevičius on 19.2.12.
 */
@Component
public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto map(User user) {
        return new UserDto(user.getUsername());
    }

}
