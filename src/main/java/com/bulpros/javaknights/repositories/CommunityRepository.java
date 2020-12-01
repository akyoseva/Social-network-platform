package com.bulpros.javaknights.repositories;

import com.bulpros.javaknights.models.Community;
import com.bulpros.javaknights.models.dto.CommunityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {
    Community findById(long id);
    Community findByTitle(String title);

    void deleteById(Long id);
    void deleteByTitle(String title);

    List<Community> findAll();
}
