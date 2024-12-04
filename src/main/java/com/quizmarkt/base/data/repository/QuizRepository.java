package com.quizmarkt.base.data.repository;

import com.quizmarkt.base.data.entity.Quiz;
import com.quizmarkt.base.data.entity.QuizGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author anercan
 */

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findAllByQuizGroupListContainingAndActiveOrderByPriorityAsc(QuizGroup quizGroupList, boolean active, Pageable pageable); //todo cache
    List<Quiz> findAllBy(Pageable pageable);
    List<Quiz> findAllByQuizGroupListContaining(QuizGroup quizGroup,Pageable pageable);
    int countAllByQuizGroupListContainingAndActive(QuizGroup quizGroupList, boolean active);
    int countAllByActiveAndAppId(boolean active,int appId);

}
