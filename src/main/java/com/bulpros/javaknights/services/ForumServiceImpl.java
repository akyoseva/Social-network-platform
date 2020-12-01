package com.bulpros.javaknights.services;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Community;
import com.bulpros.javaknights.models.Forum;
import com.bulpros.javaknights.models.User;
import com.bulpros.javaknights.repositories.CommunityRepository;
import com.bulpros.javaknights.repositories.ForumRepository;
import com.bulpros.javaknights.repositories.UserRepository;
import com.bulpros.javaknights.services.contracts.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumServiceImpl implements ForumService {
    private ForumRepository forumRepository;
    private UserRepository userRepository;
    private CommunityRepository communityRepository;

    @Autowired
    public ForumServiceImpl(ForumRepository forumRepository, UserRepository userRepository, CommunityRepository communityRepository) {
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
    }

    @Override
    public void createForum(User user, Forum forum, long communityId) throws InvalidUserException {
        if(!isAdmin(user.getUsername())){
            throw new InvalidUserException("This user can't add this forum!");
        }
        Community community=communityRepository.findById(communityId);
        forum.setCommunity(community);
        forumRepository.save(forum);
        community.getForums().add(forum);
        communityRepository.save(community);
    }

    @Override
    public void editTitle(User user, long forumId, String title) throws InvalidUserException {
        if(!isAdmin(user.getUsername())){
            throw new InvalidUserException("This user can't add this forum!");
        }
        Forum forum = getForum(forumId);
        forum.setTitle(title);
        forumRepository.save(forum);
    }

    @Override
    public void editDescription(User user, long forumId, String description) throws InvalidUserException {
        if(!isAdmin(user.getUsername())){
            throw new InvalidUserException("This user can't update this forum!");
        }
        Forum forum = getForum(forumId);
        forum.setDescription(description);
        forumRepository.save(forum);
    }

    @Transactional
    @Override
    public void delete(User user, long forumId, long communityId) throws InvalidUserException {
        if(!isAdmin(user.getUsername())){
            throw new InvalidUserException("This user can't remove this forum!");
        }

        Community community=communityRepository.findById(communityId);
        Forum forum=getForum(forumId);
        community.getForums().remove(forum);
        communityRepository.save(community);
        forumRepository.delete(forum);
    }

    @Override
    public List<Forum> getAll() {
        return forumRepository.findAll();
    }

    @Override
    public Forum getForum(long forumId) {
        return forumRepository.findById(forumId);
    }

    @Override
    public List<Forum> getCommunityForums(long communityId) {
        Community community=communityRepository.findById(communityId);
        return (List<Forum>) community.getForums();
    }

    private boolean isAdmin(String username) {
        User user = userRepository.findByUsername(username);
        return user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
