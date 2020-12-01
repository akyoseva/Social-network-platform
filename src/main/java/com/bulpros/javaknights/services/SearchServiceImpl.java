package com.bulpros.javaknights.services;

import com.bulpros.javaknights.models.*;
import com.bulpros.javaknights.repositories.*;
import com.bulpros.javaknights.services.contracts.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ForumRepository forumRepository;
    private CommunityRepository communityRepository;

    @Autowired
    public SearchServiceImpl(UserRepository userRepository,
                             CommentRepository commentRepository,
                             PostRepository postRepository,
                             ForumRepository forumRepository,
                             CommunityRepository communityRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.forumRepository = forumRepository;
        this.communityRepository = communityRepository;
    }

    @Override
    public List<User> searchUser(String text) {
        List<User> all=userRepository.findAll();
        List<User> result=new ArrayList<>();
        for(User user:all){
            if(user.getUsername().contains(text) || user.getEmail().contains(text)){
                result.add(user);
            }
        }
        return result;
    }

    @Override
    public List<Comment> searchComment(String text) {
        List<Comment> all=commentRepository.findAll();
        List<Comment> result = new ArrayList<>();
        for(Comment comment:all){
            if(comment.getText().contains(text)){
                result.add(comment);
            }
        }
        return result;
    }

    @Override
    public List<Post> searchPost(String text) {
        List<Post> all=postRepository.findAll();
        List<Post> result = new ArrayList<>();
        for(Post post:all){
            if(post.getText().contains(text) || post.getTitle().contains(text)){
                result.add(post);
            }
        }
        return result;
    }

    @Override
    public List<Forum> searchForum(String text) {
        List<Forum> all=forumRepository.findAll();
        List<Forum> result = new ArrayList<>();
        for(Forum forum:all){
            if(forum.getTitle().contains(text) || forum.getDescription().contains(text)){
                result.add(forum);
            }
        }
        return result;
    }

    @Override
    public List<Community> searchCommunity(String text) {
        List<Community> all=communityRepository.findAll();
        List<Community> result = new ArrayList<>();
        for(Community community:all){
            if(community.getTitle().contains(text) || community.getDescription().contains(text)){
                result.add(community);
            }
        }
        return result;
    }
}
