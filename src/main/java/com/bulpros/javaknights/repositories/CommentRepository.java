package com.bulpros.javaknights.repositories;

import com.bulpros.javaknights.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment findById(long id);
    void deleteById(Long id);
    void deleteAllByPostId(Long postId);
    List<Comment> findAllByPostId(Long postId);
    List<Comment> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
    List<Comment> findAll();
}
