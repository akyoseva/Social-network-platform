package com.bulpros.javaknights.services.contracts;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Forum;
import com.bulpros.javaknights.models.User;

import java.util.List;

public interface ForumService {
    void createForum(User user, Forum forum, long communityId) throws InvalidUserException;
    void editTitle(User user,long forumId, String title) throws InvalidUserException;
    void editDescription(User user,long forumId, String description) throws InvalidUserException;
    void delete(User user,long forumId, long communityId) throws InvalidUserException;

    List<Forum> getAll();
    Forum getForum(long forumId);
    List<Forum> getCommunityForums(long communityId);

}
