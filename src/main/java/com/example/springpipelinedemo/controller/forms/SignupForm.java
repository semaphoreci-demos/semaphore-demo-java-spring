package com.example.springpipelinedemo.controller.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Created by Rimantas Jacikevičius on 19.2.14.
 */
public class SignupForm {

    public final String email;
    public final String password;

    @JsonCreator
    public SignupForm(
            @JsonProperty("email") @Email @NotNull String email,
            @JsonProperty("password") @NotNull String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignupForm{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
