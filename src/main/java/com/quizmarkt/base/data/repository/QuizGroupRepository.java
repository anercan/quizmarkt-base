package com.quizmarkt.base.data.repository;

import com.quizmarkt.base.data.constant.CacheConstants;
import com.quizmarkt.base.data.entity.QuizGroup;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author anercan
 */

@Repository
public interface QuizGroupRepository extends JpaRepository<QuizGroup, Long> {

    @Cacheable(value = CacheConstants.QUIZ_GROUPS, key = "#appId")
    @EntityGraph(attributePaths = "attributes")
    List<QuizGroup> findAllByActiveAndAppIdOrderByPriorityAsc(boolean active, Integer appId);
    List<QuizGroup> findAllByAppId(int appId, Pageable pageable);

}
