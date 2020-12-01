package com.bulpros.javaknights.services.contracts;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Community;
import com.bulpros.javaknights.models.User;

import java.util.List;

public interface CommunityService {
    void createCommunity(Community community, String username) throws InvalidUserException;
    void delete(long communityId, String username) throws InvalidUserException;
    void editTitle(String title, long communityId, User user) throws InvalidUserException;
    void editDescription(String description, long communityId, User user) throws InvalidUserException;
    void join(User user, long communityId);
    void leave(User user, long communityId);
    List<Community> getAll();
    Community getCommunity(long communityId);
}
