package com.quesmarkt.quesmarktbase.data.repository;

import com.quesmarkt.quesmarktbase.data.entity.Answers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author anercan
 */

@Repository
public interface AnswerRepository extends JpaRepository<Answers, Long> {

}
