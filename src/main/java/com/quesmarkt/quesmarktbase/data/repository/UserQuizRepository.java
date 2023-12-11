package com.quesmarkt.quesmarktbase.data.repository;

import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author anercan
 */

@Repository
public interface UserQuizRepository extends JpaRepository<UserQuiz, Long> {

}
