package com.bulpros.javaknights.controllers;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Forum;
import com.bulpros.javaknights.models.User;
import com.bulpros.javaknights.models.dto.ForumDto;
import com.bulpros.javaknights.services.contracts.CommunityService;
import com.bulpros.javaknights.services.contracts.ForumService;
import com.bulpros.javaknights.services.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ForumController {
    private ForumService forumService;
    private UserService userService;
    private CommunityService communityService;
    private ModelMapper modelMapper;

    @Autowired
    public ForumController(ForumService forumService, UserService userService, CommunityService communityService, ModelMapper modelMapper) {
        this.forumService = forumService;
        this.userService = userService;
        this.communityService = communityService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/forum-card/{id}")
    public String showForumInformation(@PathVariable Long id, Model model) {
        model.addAttribute("currentForum", forumService.getForum(id));
        model.addAttribute("user",userService.getPrincipal());
        return "forum_card";
    }

    @GetMapping(value = "/communities/community-card/{id}/create-forum")
    public String showCreateForum(@PathVariable long id, Model model) {
        model.addAttribute("currentCommunity", communityService.getCommunity(id));
        model.addAttribute("forum", new Forum());
        return "create_forum";
    }

    @PostMapping(value = "/communities/community-card/{id}/create-forum")
    public String createForum(@PathVariable long id, @Valid @ModelAttribute ForumDto forumDto) throws InvalidUserException {
        User user = userService.getPrincipal();
        Forum forum=modelMapper.map(forumDto,Forum.class);
        forumService.createForum(user, forum, id);
        return "redirect:/communities/community-card/" + id;
    }

    @PostMapping(value="/forum-card/{id}/delete")
    public String deleteForum(@PathVariable long id) throws InvalidUserException {
        User user=userService.getPrincipal();
        Forum forum=forumService.getForum(id);
        long communityId=forum.getCommunity().getId();
        forumService.delete(user,id,communityId);
        return "redirect:/communities/community-card/"+communityId;
    }
}
