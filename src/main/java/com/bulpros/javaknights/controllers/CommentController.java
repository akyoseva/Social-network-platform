package com.bulpros.javaknights.controllers;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Comment;
import com.bulpros.javaknights.models.Post;
import com.bulpros.javaknights.models.User;
import com.bulpros.javaknights.models.dto.CommentDto;
import com.bulpros.javaknights.services.contracts.CommentService;
import com.bulpros.javaknights.services.contracts.PostService;
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
public class CommentController {
    private PostService postService;
    private UserService userService;
    private ModelMapper modelMapper;
    private CommentService commentService;

    @Autowired
    public CommentController(PostService postService,
                             UserService userService,
                             ModelMapper modelMapper,
                             CommentService commentService) {
        this.postService = postService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.commentService = commentService;
    }

    @PostMapping(value = "/forum-card/post/{id}/create-comment")
    public String showCommentCreateSubmit(@PathVariable long id, @Valid @ModelAttribute CommentDto commentDto, Model model) {
        User user = userService.getPrincipal();
        Post post=postService.getPost(id);
        Comment comment=modelMapper.map(commentDto,Comment.class);
        postService.addComment(comment,id,user.getUsername());

        return "redirect:/forum-card/"+post.getForum().getId();
    }

    @GetMapping(value = "/forum-card/post/{id}/create-comment")
    public String showCommentCreate(@PathVariable long id, Model model) {
        model.addAttribute("post",postService.getPost(id));
        model.addAttribute("comment",new Comment());
        model.addAttribute("currentUser", userService.getPrincipal());

        return "create_comment";
    }

    @PostMapping(value="/forum-card/post/delete-comment/{id}")
    public String deleteComment(@PathVariable long id, Model model) throws InvalidUserException {
        model.addAttribute("currentUser", userService.getPrincipal());
        Comment comment=commentService.getComment(id);
        commentService.delete(userService.getPrincipal().getUsername(),id);
        return "redirect:/forum-card/"+comment.getPost().getForum().getId();
    }
}
