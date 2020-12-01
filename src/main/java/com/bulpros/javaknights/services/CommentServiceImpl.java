package com.bulpros.javaknights.services;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Comment;
import com.bulpros.javaknights.models.Post;
import com.bulpros.javaknights.models.User;
import com.bulpros.javaknights.repositories.CommentRepository;
import com.bulpros.javaknights.repositories.PostRepository;
import com.bulpros.javaknights.repositories.UserRepository;
import com.bulpros.javaknights.services.contracts.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository,
                              UserRepository userRepository,
                              PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Comment getComment(long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public void edit(String text, String username, long commentId) throws InvalidUserException {
        Comment comment = getComment(commentId);
        if (isAdmin(username) || isOwner(username, comment)) {
            comment.setText(text);
            commentRepository.save(comment);
        } else {
            throw new InvalidUserException("This user can't update this comment!");
        }
    }

    @Transactional
    @Override
    public void delete(String username, long commentId) throws InvalidUserException {
        Comment comment = getComment(commentId);
        if (!isAdmin(username) && !isOwner(username, comment)) {
            throw new InvalidUserException("This user can't remove this comment!");
        }
        Post post = comment.getPost();
        post.getComments().remove(comment);
        User user = comment.getUser();
        user.getComments().remove(comment);
        commentRepository.delete(comment);
        postRepository.save(post);
        userRepository.save(user);
    }

    private boolean isOwner(String username, Comment comment){
        User user=userRepository.findByUsername(username);
        return user.getId()==comment.getUser().getId();
    }

    private boolean isAdmin(String username) {
        User user = userRepository.findByUsername(username);
        return user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
