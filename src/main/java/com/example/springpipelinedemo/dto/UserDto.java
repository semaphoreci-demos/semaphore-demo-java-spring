package com.example.springpipelinedemo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Rimantas Jacikevičius on 19.2.12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    public final String username;

    @JsonCreator
    public UserDto(@JsonProperty("username") String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                '}';
    }

}
