package com.quizmarkt.base.data.repository;

import com.quizmarkt.base.data.entity.UserDailyQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author anercan
 */
@Repository
public interface UserDailyQuizRepository extends JpaRepository<UserDailyQuiz, Long> {

    Optional<UserDailyQuiz> findByAppIdAndUserIdAndCreatedDate(int appId, String userId, LocalDate createdDate);

    boolean existsByAppIdAndUserId(int appId, String userId);

}
