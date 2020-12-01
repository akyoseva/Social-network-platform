package com.bulpros.javaknights.controllers;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Forum;
import com.bulpros.javaknights.models.Post;
import com.bulpros.javaknights.models.User;
import com.bulpros.javaknights.models.dto.PostDto;
import com.bulpros.javaknights.services.contracts.PostService;
import com.bulpros.javaknights.services.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PostController {
    private PostService postService;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public PostController(PostService postService, UserService userService, ModelMapper modelMapper) {
        this.postService = postService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/forum-card/{id}/create-post")
    public String showPostCreateSubmit(@PathVariable long id, @Valid @ModelAttribute PostDto postDto) {
        User user = userService.getPrincipal();
        Post post=modelMapper.map(postDto,Post.class);
        postService.createPost(post,user.getUsername(),id);

        return "redirect:/forum-card/"+id;
    }

    @GetMapping(value = "/forum-card/{id}/create-post")
    public String showPostCreate(@PathVariable long id, Model model) {
        model.addAttribute("currentForum",id);
        model.addAttribute("post",new Post());
        model.addAttribute("currentUser", userService.getPrincipal());

        return "create_post";
    }

    @GetMapping(value="/post/{id}/show-comments")
    public String showComments(@PathVariable long id, Model model){
        model.addAttribute("comments",postService.getPost(id).getComments());
        model.addAttribute("currentUser",userService.getPrincipal());
        return "show_comments";
    }

    @PostMapping(value="/forum-card/post/{id}/delete")
    public String deletePost(@PathVariable long id) throws InvalidUserException {
        Post post=postService.getPost(id);
        long forumId=post.getForum().getId();
        postService.delete(id,post.getForum().getId(),userService.getPrincipal().getUsername());
        return "redirect:/forum-card/"+forumId;
    }
}
