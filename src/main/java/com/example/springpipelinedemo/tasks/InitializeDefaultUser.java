package com.example.springpipelinedemo.tasks;

import com.example.springpipelinedemo.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by Rimantas Jacikevičius on 19.2.12.
 */
@Component
@ConditionalOnExpression("not ${testContext:false}")
public class InitializeDefaultUser implements ApplicationListener<ApplicationReadyEvent> {

    private final UserService userService;
    private final String defaultUsername;
    private final String defaultPassword;

    public InitializeDefaultUser(UserService userService,
                                 @Value("${defaultUser}") String defaultUsername,
                                 @Value("${defaultPassword}") String defaultPassword) {
        this.userService = userService;
        this.defaultUsername = defaultUsername;
        this.defaultPassword = defaultPassword;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        userService.createUser(defaultUsername, defaultPassword);
    }

}