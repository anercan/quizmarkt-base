package com.quizmarkt.base.data.repository;

import com.quizmarkt.base.data.constant.CacheConstants;
import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author anercan
 */

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Cacheable(value = CacheConstants.QUIZ_LIST, key = "#quizGroup.id")
    List<Quiz> findAllByQuizGroupListContainingAndActiveOrderByPriorityAsc(QuizGroup quizGroup, boolean active, Pageable pageable);

    List<Quiz> findAllByAppId(int appId,Pageable pageable);

    List<Quiz> findAllByQuizGroupListContainingOrderByPriorityAsc(QuizGroup quizGroup, Pageable pageable);

    int countAllByQuizGroupListContainingAndActive(QuizGroup quizGroupList, boolean active);

    @Cacheable(value = CacheConstants.QUIZ_COUNT, key = "#appId")
    int countAllByActiveAndAppId(boolean active, int appId);

    //@EntityGraph(attributePaths = {"questionList"})
    @Query("SELECT quiz FROM Quiz quiz " +
            "LEFT JOIN FETCH quiz.questionList q " +
            "WHERE quiz.id = :quizId and q.active = true " +
            "ORDER BY q.priority ASC")
    Optional<Quiz> findQuizWithQuestionsSorted(@Param("quizId") Long quizId); // todo check joins entity graph

}
