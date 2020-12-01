package com.bulpros.javaknights.services.contracts;

import com.bulpros.javaknights.models.*;

import java.util.List;

public interface SearchService {
    List<User> searchUser(String text);
    List<Comment> searchComment(String text);
    List<Post> searchPost(String text);
    List<Forum> searchForum(String text);
    List<Community> searchCommunity(String text);
}
