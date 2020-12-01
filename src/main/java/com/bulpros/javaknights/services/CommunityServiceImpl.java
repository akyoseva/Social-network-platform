package com.bulpros.javaknights.services;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Community;
import com.bulpros.javaknights.models.User;
import com.bulpros.javaknights.repositories.CommunityRepository;
import com.bulpros.javaknights.repositories.UserRepository;
import com.bulpros.javaknights.services.contracts.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {
    private CommunityRepository communityRepository;
    private UserRepository userRepository;

    @Autowired
    public CommunityServiceImpl(CommunityRepository communityRepository, UserRepository userRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void createCommunity(Community community, String username) throws InvalidUserException {
//        if (!isAdmin(username.getUsername())) {
//            throw new InvalidUserException("This user isn't admin!");
//        }
        communityRepository.save(community);
    }

    @Transactional
    @Override
    public void delete(long communityId, String username) throws InvalidUserException {
        Community community = getCommunity(communityId);
//        if (!isAdmin(username.getUsername())) {
//            throw new InvalidUserException("This user isn't admin! You can't delete this community!");
//        }
        communityRepository.delete(community);
    }

    @Override
    public void editTitle(String title, long communityId, User user) throws InvalidUserException {
        Community community = getCommunity(communityId);
        if (!isAdmin(user.getUsername())) {
            throw new InvalidUserException("This user isn't admin! You can't update this community!");
        }
        community.setTitle(title);
        communityRepository.save(community);
    }

    @Override
    public void editDescription(String description, long communityId, User user) throws InvalidUserException {
        Community community = getCommunity(communityId);
        if (!isAdmin(user.getUsername())) {
            throw new InvalidUserException("This user isn't admin! You can't update this community!");
        }

        community.setDescription(description);
        communityRepository.save(community);
    }


    @Override
    public void join(User user, long communityId) {
        Community community = getCommunity(communityId);
        community.getMembers().add(user);
        user.getCommunities().add(community);
        communityRepository.save(community);
        userRepository.save(user);
    }

    @Override
    public void leave(User user, long communityId) {
        Community community = getCommunity(communityId);
        community.getMembers().remove(user);
        user.getCommunities().remove(community);
        communityRepository.save(community);
        userRepository.save(user);
    }

    @Override
    public List<Community> getAll() {
        return communityRepository.findAll();
    }

    @Override
    public Community getCommunity(long communityId) {
        return communityRepository.findById(communityId);
    }


    private boolean isAdmin(String username) {
        User user = userRepository.findByUsername(username);
        return user.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
