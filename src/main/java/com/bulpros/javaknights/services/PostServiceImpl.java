package com.bulpros.javaknights.services;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Comment;
import com.bulpros.javaknights.models.Forum;
import com.bulpros.javaknights.models.Post;
import com.bulpros.javaknights.models.User;
import com.bulpros.javaknights.repositories.CommentRepository;
import com.bulpros.javaknights.repositories.ForumRepository;
import com.bulpros.javaknights.repositories.PostRepository;
import com.bulpros.javaknights.repositories.UserRepository;
import com.bulpros.javaknights.services.contracts.PostService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private ForumRepository forumRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           CommentRepository commentRepository,
                           UserRepository userRepository,
                           ForumRepository forumRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.forumRepository = forumRepository;
    }


    @Override
    public void createPost(Post post, String username, long forumId) {
        Forum forum = forumRepository.findById(forumId);
        User user=userRepository.findByUsername(username);
        post.setUser(user);
        post.setForum(forum);

        postRepository.save(post);
        forum.getPosts().add(post);
        forumRepository.save(forum);
        user.getPosts().add(post);
        userRepository.save(user);
    }

    @Override
    public Post getPost(long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public void addComment(Comment comment, long postId,String username) {
        Post post = getPost(postId);
        User user=userRepository.findByUsername(username);
        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);
        post.getComments().add(comment);
        postRepository.save(post);
        user.getComments().add(comment);
        userRepository.save(user);

    }

    @Transactional
    @Override
    public void delete(long postId, long forumId, String username) throws InvalidUserException {
        Post post = getPost(postId);
        User user=userRepository.findByUsername(username);
        if (!isAdmin(username) && !isOwner(username, post)) {
            throw new InvalidUserException("You can't remove this post!");
        }
        Forum forum=post.getForum();
        forum.getPosts().remove(post);
        user.getPosts().remove(post);
        postRepository.delete(post);
        forumRepository.save(forum);
        userRepository.save(user);
    }

    @Override
    public void editTitle(String title, long postId, String username) throws InvalidUserException {
        Post post = getPost(postId);
        if (isAdmin(username) || isOwner(username, post)) {
            post.setTitle(title);
            postRepository.save(post);
        } else {
            throw new InvalidUserException("You can't remove this post!");
        }
    }

    @Override
    public void editDescription(String description, long postId, String username) throws InvalidUserException {
        Post post = getPost(postId);
        if (isAdmin(username) || isOwner(username, post)) {
            post.setText(description);
            postRepository.save(post);
        } else {
            throw new InvalidUserException("You can't remove this post!");
        }
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    @Override
    public List<Comment> getComments(long postId) {
        Post currentPost = getPost(postId);
        Hibernate.initialize(currentPost.getComments());
        return (List<Comment>) currentPost.getComments();

    }

    private boolean isOwner(String username, Post post) {
        User user = userRepository.findByUsername(username);
        return user.getId() == post.getUser().getId();
    }

    private boolean isAdmin(String username) {
        User user = userRepository.findByUsername(username);
        return user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
