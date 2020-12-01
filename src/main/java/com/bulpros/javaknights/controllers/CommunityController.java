package com.bulpros.javaknights.controllers;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Community;
import com.bulpros.javaknights.models.User;
import com.bulpros.javaknights.models.dto.CommunityDto;
import com.bulpros.javaknights.services.contracts.AuthorityService;
import com.bulpros.javaknights.services.contracts.CommunityService;
import com.bulpros.javaknights.services.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class CommunityController {
    private CommunityService communityService;
    private UserService userService;
    private ModelMapper modelMapper;
    private AuthorityService authorityService;

    @Autowired
    public CommunityController(CommunityService communityService, UserService userService, ModelMapper modelMapper, AuthorityService authorityService) {
        this.communityService = communityService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authorityService = authorityService;
    }

    @RequestMapping(value = "/communities", method = RequestMethod.GET)
    public String showCommunities(Model model){
        User user=userService.getPrincipal();
        model.addAttribute("user",userService.getPrincipal());
        model.addAttribute("communities", communityService.getAll());
        model.addAttribute("isAdmin",authorityService.isAdmin(user.getUsername()));

        return "communities";
    }

    @PostMapping(value="/communities/join/{id}")
    public String joinToCommunity(@PathVariable Long id, Principal principal) throws InvalidUserException {
        User currentUser = userService.getUser(principal.getName());
        communityService.join(currentUser,id);
        return "redirect:/communities";
    }

    @PostMapping(value="/communities/leave/{id}")
    public String leaveCommunity(@PathVariable Long id, Principal principal) throws InvalidUserException {
        User currentUser = userService.getUser(principal.getName());
        communityService.leave(currentUser,id);
        return "redirect:/communities";
    }



    @GetMapping(value="/communities/community-card/{id}")
    public String showCommunityInformation(@PathVariable long id, Model model){
        model.addAttribute("currentCommunity",communityService.getCommunity(id));

        return "community_card";
    }

    @GetMapping(value="/create-community")
    public String showCreateCommunity(Model model){
        model.addAttribute("community",new Community());
        return "create_community";
    }

    @PostMapping(value = "/create-community")
    public String createCommunity(@Valid @ModelAttribute CommunityDto communitytDto) throws InvalidUserException {
        User user=userService.getPrincipal();
        Community community=modelMapper.map(communitytDto,Community.class);
        communityService.createCommunity(community,user.getUsername());
        return "redirect:/communities";
    }

    @PostMapping(value="/communities/delete/{id}")
    public String deleteCommunity(@PathVariable long id) throws InvalidUserException {
        User user=userService.getPrincipal();
        communityService.delete(id,user.getUsername());
        return "redirect:/communities";
    }
}
