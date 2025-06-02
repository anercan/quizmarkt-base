package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.constant.CacheConstants;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

/**
 * @author anercan
 */

@Component
@AllArgsConstructor
public class CacheProviderManager extends BaseManager {

    private final CacheManager cacheManager;

    public Cache getCache(String cacheName) {
        return cacheManager.getCache(cacheName);
    }

    @CacheEvict(value = CacheConstants.USER_QUIZ_LIST, key = "#appId + #userId")
    public void evictUserQuizListCache(String userId, Integer appId) {
        // This method is used to evict the USER_QUIZ_LIST cache
    }

    @CacheEvict(value = CacheConstants.USER_DATA, key = "#userId")
    public void evictUserDataCache(String userId) {
        // This method is used to evict the USER_DATA cache
    }

    @CacheEvict(value = CacheConstants.USER_QUIZ_FOR_ANALYTICS, key = "#userId")
    public void evictUserQuizForAnalyticsCache(String userId) {
        // This method is used to evict the USER_QUIZ_FOR_ANALYTICS cache
    }

    @CacheEvict(value = {CacheConstants.QUIZ_COUNT, CacheConstants.QUIZ_LIST, CacheConstants.QUIZ_GROUPS, CacheConstants.QUIZ, CacheConstants.USER_DATA}, allEntries = true)
    public void evictQuizRelatedCaches() {
    }

    @CacheEvict(value = {CacheConstants.QUIZ_GROUPS}, allEntries = true)
    public void evictQuizGroupRelatedCaches() {
    }

    @CacheEvict(value = {CacheConstants.QUIZ_COUNT, CacheConstants.QUIZ_LIST, CacheConstants.QUIZ}, allEntries = true)
    public void evictQuestionRelatedCaches() {
    }


}
