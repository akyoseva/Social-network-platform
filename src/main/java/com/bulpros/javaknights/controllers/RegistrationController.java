package com.bulpros.javaknights.controllers;

import com.bulpros.javaknights.models.User;
import com.bulpros.javaknights.models.dto.UserDto;
import com.bulpros.javaknights.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private UserService userService;
//    private UserDetailsManager userDetailsManager;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
//        this.userDetailsManager = userDetailsManager;
    }

//    @GetMapping("/register")
//    public String showRegistrationForm(WebRequest request, Model model) {
//        UserDto userDto = new UserDto();
//        model.addAttribute("user", userDto);
//        return "register";
//    }

    @GetMapping("/register")
    public ModelAndView test () {
        ModelAndView test = new ModelAndView("register");
        test.addObject("user", new UserDto());
        return test;
    }

//    @GetMapping("/register")
//    public String showRegisterPage(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserDto user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Username/password/email can't be empty!");
            return "register";
        }

        if(userService.getAllByUsername().contains(user.getUsername())) {
            model.addAttribute("error", "User with same username already exists!");
            return "register";
        }

        User existingUser = userService.getUserByEmail(user.getEmail());
        if(existingUser != null)
        {
            model.addAttribute("error","This email already exists!");
            return "register";
        }

        if(!user.getPassword().equals(user.getMatchingPassword())) {
            model.addAttribute("error", "Password doesn't match!");
            return "register";
        }

        userService.registerUser(user);

        return "redirect:/"+"index";
    }

}
