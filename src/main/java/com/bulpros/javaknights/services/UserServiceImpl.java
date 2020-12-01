package com.bulpros.javaknights.services;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.*;
import com.bulpros.javaknights.models.dto.UserDto;
import com.bulpros.javaknights.repositories.CommentRepository;
import com.bulpros.javaknights.repositories.PostRepository;
import com.bulpros.javaknights.repositories.UserRepository;
import com.bulpros.javaknights.services.contracts.AuthorityService;
import com.bulpros.javaknights.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthorityService authorityService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           CommentRepository commentRepository,
                           PostRepository postRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           AuthorityService authorityService) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authorityService = authorityService;
    }

    @Override
    public User getUser(String username) throws InvalidUserException {
        User user= userRepository.findByUsername(username);
        if(user==null){
            throw new InvalidUserException("This user doesn't exist!");
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<String> getAllByUsername() {
        return userRepository.getAllUsername();
    }

    @Override
    public void friend(User user, User friend) {
        user.getFriends().add(friend);
        userRepository.save(user);
    }

    @Override
    public void unfriend(User user, User friend) throws InvalidUserException {
        if(!user.getFriends().contains(friend)){
            throw new InvalidUserException("You can't unfriend this user!");
        }
        user.getFriends().remove(friend);
        userRepository.save(user);
    }

    @Override
    public void follow(long userId, long followingId) {
        User user=userRepository.findById(userId);
        User following=userRepository.findById(followingId);
        user.getFollowing().add(following);
        following.getFollowers().add(user);
        userRepository.save(user);
        userRepository.save(following);
    }

    @Override
    public void unfollow(long userId, long followingId) throws InvalidUserException {
        User user=userRepository.findById(userId);
        User following=userRepository.findById(followingId);
        if(!user.getFollowing().contains(following)){
            throw new InvalidUserException("You can't unfollow this user!");
        }
        user.getFollowing().remove(following);
        following.getFollowers().remove(user);
        userRepository.save(user);
        userRepository.save(following);
    }

    @Override
    public List<User> getFriends(User user) {
        return (List<User>) user.getFriends();
    }

    @Override
    public List<User> getFollowers(User user) {
        return (List<User>) user.getFollowers();
    }

    @Override
    public List<User> getFollowing(User user) {
        return (List<User>) user.getFollowing();
    }

    @Override
    public List<Post> getPosts(User user) {
        return postRepository.findAllByUserId(user.getId());
    }

    @Override
    public List<Community> getCommunities(User user) {
        return null;
    }

    @Override
    public List<Comment> getComments(User user) {
        return commentRepository.findAllByUserId(user.getId());
    }

    @Override
    public void changePassword(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setAuthorities(new HashSet<>());
        Authority role_user = authorityService.getAuthority("ROLE_USER");
        user.getAuthorities().add(role_user);
        userRepository.save(user);
    }

    @Override
    public User getPrincipal() {
        long id= ((User)(SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId();
        return userRepository.findById(id);
    }

    @Override
    public void deleteByAdmin(long adminId, long userId) throws InvalidUserException {
        User admin=userRepository.findById(adminId);
        User user=userRepository.findById(userId);
        if(!isAdmin(admin.getUsername())){
            throw new InvalidUserException("You can't delete this user!");
        }
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user=userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User doesn't exist!");
        }
        return user;
    }

    private boolean isAdmin(String username) {
        User user = userRepository.findByUsername(username);
        return user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
