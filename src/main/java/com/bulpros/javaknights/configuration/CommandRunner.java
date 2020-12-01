package com.bulpros.javaknights.configuration;

import com.bulpros.javaknights.models.*;
import com.bulpros.javaknights.models.dto.UserDto;
import com.bulpros.javaknights.repositories.*;
import com.bulpros.javaknights.services.contracts.*;
import io.micrometer.core.instrument.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class CommandRunner implements CommandLineRunner {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ForumRepository forumRepository;
    private CommunityRepository communityRepository;
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;

    private UserService userService;
    private CommentService commentService;
    private PostService postService;
    private ForumService forumService;
    private CommunityService communityService;
    private AuthorityService authorityService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public CommandRunner(CommentRepository commentRepository, PostRepository postRepository,
                         ForumRepository forumRepository, CommunityRepository communityRepository,
                         UserRepository userRepository, AuthorityRepository authorityRepository,
                         UserService userService, CommentService commentService,
                         PostService postService, ForumService forumService,
                         CommunityService communityService, AuthorityService authorityService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.forumRepository = forumRepository;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userService = userService;
        this.commentService = commentService;
        this.postService = postService;
        this.forumService = forumService;
        this.communityService = communityService;
        this.authorityService = authorityService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
//        Community community=new Community();
//        community.setTitle("New community for JS");
//        community.setDescription("Some text..");
//        Community community = communityService.getCommunity(1);
//        User user = userService.getUser("anidkyoseva");
//        User user1 = userService.getUser("gotin_user");
//        communityService.createCommunity(community, user);
//        communityService.join(user1, community.getId());
//        Forum forum=new Forum();
//        forum.setTitle("Java basic");
//        forum.setDescription("Some text..");
//        //forum.setCommunity(community);
//        forumService.createForum(user,forum,community.getId());
//        Forum forum=forumService.getForum(1);
//        Post post=new Post();
//        post.setTitle("How to learn Java for 24h..");
//        post.setText("Some text..");
//        postService.createPost(post,user1.getUsername(),forum.getId());
//        User user2=userService.getUser("ivan_petrov");
       // System.out.println(user2.getId());
//        Post post=postService.getPost(1);
//        post.getComments().forEach(comment -> System.out.println(comment.getText()));
//        Comment comment=new Comment();
//        comment.setText("Very nice");
       // postService.addComment(comment,post.getId(),user2.getUsername());
//      commentService.delete(user2.getUsername(),1);
//        commentService.delete("ivan_petrov", 2);
//        userRepository.findByUsername("ivan_petrov").getComments().remove(commentRepository.findById(2));
//        postRepository.findById(1).getComments().remove(commentRepository.findById(2));
//        commentRepository.delete(commentRepository.findById(2));

    }
}
