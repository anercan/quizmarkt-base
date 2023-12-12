package com.quesmarkt.quesmarktbase.data.repository;

import com.quesmarkt.quesmarktbase.data.entity.QuizGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author anercan
 */

@Repository
public interface QuizGroupRepository extends JpaRepository<QuizGroup, Long> {

    //todo cache
    List<QuizGroup> findAllByActiveAndAppId(boolean active, int appId, Pageable pageable);

}
