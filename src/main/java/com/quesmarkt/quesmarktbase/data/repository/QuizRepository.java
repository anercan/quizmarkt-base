package com.quesmarkt.quesmarktbase.data.repository;

import com.quesmarkt.quesmarktbase.data.entity.Quiz;
import com.quesmarkt.quesmarktbase.data.entity.QuizGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author anercan
 */

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findAllByQuizGroupListContainingAndActive(QuizGroup quizGroupList, boolean active, Pageable pageable); //todo cache
    List<Quiz> findAllBy(Pageable pageable);
}
