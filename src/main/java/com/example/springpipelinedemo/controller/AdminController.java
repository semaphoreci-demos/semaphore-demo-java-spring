package com.example.springpipelinedemo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Rimantas Jacikevičius on 19.2.14.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/home")
    public ModelAndView home(@AuthenticationPrincipal UserDetails details, Model model) {
        model.addAttribute("user", details);

        return new ModelAndView("home");
    }

}
