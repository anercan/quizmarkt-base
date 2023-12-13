package com.quesmarkt.quesmarktbase.data.repository;

import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author anercan
 */

@Repository
public interface UserQuizRepository extends JpaRepository<UserQuiz, Long> {

    List<UserQuiz> findAllByAppIdAndUserIdAndQuizGroupIdIn(int appId, Long userId, Set<Long> quizGroupId);
    Optional<UserQuiz> findByQuiz_IdAndUserId(Long quizId, Long userId);
    List<UserQuiz> findAllByAppIdAndUserIdOrderByCompleteDate(int appId, Long userId);
}
