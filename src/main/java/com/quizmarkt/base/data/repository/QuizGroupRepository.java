package com.quizmarkt.base.data.repository;

import com.quizmarkt.base.data.entity.QuizGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author anercan
 */

@Repository
public interface QuizGroupRepository extends JpaRepository<QuizGroup, Long> {

    List<QuizGroup> findAllByActiveAndAppId(boolean active, int appId, Pageable pageable);    //todo cache
    List<QuizGroup> findAllByAppId(int appId, Pageable pageable);

}
