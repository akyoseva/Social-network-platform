package com.bulpros.javaknights.services.contracts;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Comment;
import com.bulpros.javaknights.models.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PostService {
    void createPost(Post post, String username, long forumId);

    Post getPost(long postId);

    void addComment(Comment comment, long postId,String username);

    void delete(long postId, long forumId, String username) throws InvalidUserException;

    void editTitle(String title, long postId, String username) throws InvalidUserException;

    void editDescription(String description, long postId, String username) throws InvalidUserException;

    List<Post> getAllPosts();
    List<Comment> getComments(long postId);
}
