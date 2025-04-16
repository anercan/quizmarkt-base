package com.quizmarkt.base.data.repository;

import com.quizmarkt.base.data.entity.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author anercan
 */

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = """
                SELECT q.*
                FROM question q
                         JOIN quiz_questions qq ON q.id = qq.question_id
                         JOIN quiz z ON qq.quiz_id = z.id
                WHERE z.app_id = :appId
                ORDER BY RANDOM();
            """, nativeQuery = true)
    List<Question> findRandomQuestionsWithAppId(@Param("appId") int appId, Pageable pageable);
}
