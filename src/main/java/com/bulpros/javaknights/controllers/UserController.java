package com.bulpros.javaknights.controllers;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.User;
import com.bulpros.javaknights.services.contracts.CommunityService;
import com.bulpros.javaknights.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("currentUser",userService.getPrincipal());
        model.addAttribute("users", userService.getAll());

        return "users";
    }

    @PostMapping(value="/users/follow/{id}")
    public String followUser(@PathVariable Long id, Principal principal) throws InvalidUserException {
        long currentUserID = userService.getUser(principal.getName()).getId();
        userService.follow(currentUserID,id);
        return "redirect:/users";
    }

    @PostMapping(value="/users/unfollow/{id}")
    public String unfollowUser(@PathVariable Long id, Principal principal) throws InvalidUserException {
        long currentUserID = userService.getUser(principal.getName()).getId();
        userService.unfollow(currentUserID,id);
        return "redirect:/users";
    }

    @PostMapping(value="/users/delete/{id}")
    public String deleteUser(@PathVariable long id) throws InvalidUserException {
        User admin=userService.getPrincipal();
        userService.deleteByAdmin(admin.getId(),id);
        return "redirect:/users";
    }
}
