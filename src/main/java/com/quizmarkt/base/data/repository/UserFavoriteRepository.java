package com.quizmarkt.base.data.repository;

import com.quizmarkt.base.data.entity.UserFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author anercan
 */

@Repository
public interface UserFavoriteRepository extends JpaRepository<UserFavorites, Long> {
    Optional<UserFavorites> findByUserId(String userId);
}
