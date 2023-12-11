package com.quesmarkt.quesmarktbase.data.repository;

import com.quesmarkt.quesmarktbase.data.entity.Answers;
import com.quesmarkt.quesmarktbase.data.entity.UserWrongAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author anercan
 */

@Repository
public interface UserWrongAnswerRepository extends JpaRepository<UserWrongAnswer, Long> {

}
