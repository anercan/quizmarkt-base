package com.quizmarkt.base.data.repository;

import com.quizmarkt.base.data.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author anercan
 */

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
