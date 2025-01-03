package com.quizmarkt.base.data.repository;

import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.enums.UserQuizState;
import org.springframework.data.jpa.repository.EntityGraph;
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

    @EntityGraph(attributePaths = "wrongQuestionList")
    List<UserQuiz> findAllByAppIdAndUserIdAndQuizGroupIdIn(int appId, String userId, Set<Long> quizGroupId);
    @EntityGraph(attributePaths = "wrongQuestionList")
    Optional<UserQuiz> findByQuiz_IdAndUserId(Long quizId, String userId);
    List<UserQuiz> findByQuiz_Id(Long quizId);
    List<UserQuiz> findAllByAppIdAndUserId(int appId, String userId);
    List<UserQuiz> findAllByAppIdAndUserIdOrderByCompleteDateDesc(int appId, String userId);
    long countByAppIdAndUserIdAndState(int appId, String userId, UserQuizState state);
}
