package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.cache.QuizGroupCacheable;
import com.quizmarkt.base.data.constant.CacheConstants;
import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.mapper.QuizGroupMapper;
import com.quizmarkt.base.data.repository.QuizGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@AllArgsConstructor
@Service
public class QuizGroupManager extends BaseManager {

    private final QuizGroupRepository quizGroupRepository;
    private final QuizGroupMapper quizGroupMapper;

    @Cacheable(value = CacheConstants.QUIZ_GROUPS, key = "#appId" ,unless = "#result == null or #result?.isEmpty()")
    public List<QuizGroupCacheable> getActiveQuizGroups(com.quizmarkt.base.data.request.PageRequest request, int appId) {
        try {
            List<QuizGroup> quizGroupList = quizGroupRepository.findAllByActiveAndAppIdOrderByPriorityAsc(true, appId);
            return quizGroupList.stream().map(quizGroupMapper::quizGroupToQuizGroupCacheable).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("getQuizGroups got exception request:{}", request.toString(), e);
            return Collections.emptyList();
        }
    }
}
