package com.bulpros.javaknights.services.contracts;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Comment;
import com.bulpros.javaknights.models.Community;
import com.bulpros.javaknights.models.Post;
import com.bulpros.javaknights.models.User;
import com.bulpros.javaknights.models.dto.UserDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User getUser(String username) throws InvalidUserException;
    List<User> getAll();
    List<String> getAllByUsername();

    void friend(User user, User friend);
    void unfriend(User user, User friend) throws InvalidUserException;

    void follow(long userId, long followingId);
    void unfollow(long userId, long followingId) throws InvalidUserException;

    List<User> getFriends(User user);
    List<User> getFollowers(User user);
    List<User> getFollowing(User user);

    List<Post> getPosts(User user);

    List<Community> getCommunities(User user);
    List<Comment> getComments(User user);

    void changePassword(User user, String password);

    User getUserByEmail(String email);

    void registerUser(UserDto user);
    User getPrincipal();

    void deleteByAdmin(long adminId, long userId) throws InvalidUserException;

}
