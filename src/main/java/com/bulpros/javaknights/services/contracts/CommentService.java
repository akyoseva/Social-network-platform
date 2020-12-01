package com.bulpros.javaknights.services.contracts;

import com.bulpros.javaknights.exceptions.InvalidUserException;
import com.bulpros.javaknights.models.Comment;

public interface CommentService {
    Comment getComment(long commentId);
    void edit(String text, String username, long commentId) throws InvalidUserException;
    void delete(String username, long commentId) throws InvalidUserException;
}
