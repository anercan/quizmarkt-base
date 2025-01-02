package com.quizmarkt.base.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.quizmarkt.base.data.constant.CacheConstants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.registerCustomCache(
                CacheConstants.QUIZ_GROUPS,
                Caffeine.newBuilder()
                        .maximumSize(3)
                        .build()
        );

        cacheManager.registerCustomCache(
                CacheConstants.QUIZ_COUNT,
                Caffeine.newBuilder()
                        .maximumSize(3)
                        .build()
        );

        cacheManager.registerCustomCache(
                CacheConstants.USER_INFO,
                Caffeine.newBuilder()
                        .maximumSize(20)
                        .expireAfterWrite(30, TimeUnit.MINUTES)
                        .build()
        );

        cacheManager.registerCustomCache(
                CacheConstants.QUIZ_LIST,
                Caffeine.newBuilder()
                        .maximumSize(20)
                        .build()
        );

        cacheManager.registerCustomCache(
                CacheConstants.USER_DATA,
                Caffeine.newBuilder()
                        .maximumSize(20)
                        .build()
        );

        return cacheManager;
    }
}
