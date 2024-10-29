package com.quizmarkt.base.data.repository;

import com.quizmarkt.base.data.entity.UserQuiz;
import com.quizmarkt.base.data.enums.UserQuizState;
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

    List<UserQuiz> findAllByAppIdAndUserIdAndQuizGroupIdIn(int appId, String userId, Set<Long> quizGroupId);
    //todo cache this
    Optional<UserQuiz> findByQuiz_IdAndUserId(Long quizId, String userId);
    List<UserQuiz> findAllByAppIdAndUserIdOrderByCompleteDate(int appId, String userId);
    long countByAppIdAndUserIdAndState(int appId, String userId, UserQuizState state);
}
