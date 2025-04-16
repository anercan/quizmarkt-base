package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.entity.UserDailyQuiz;
import com.quizmarkt.base.data.repository.UserDailyQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author anercan
 */

@RequiredArgsConstructor
@Service
public class UserDailyQuizManager extends BaseManager {
    private final UserDailyQuizRepository userDailyQuizRepository;

    public Optional<UserDailyQuiz> findByAppIdAndUserIdAndCreatedDate() {
        return userDailyQuizRepository.findByAppIdAndUserIdAndCreatedDate(getAppId(), getUserId(), LocalDate.now());
    }

    public boolean isExistByUserIdAndAppId() {
        return userDailyQuizRepository.existsByAppIdAndUserId(getAppId(), getUserId());
    }

    public boolean save(UserDailyQuiz entity) {
        try {
            userDailyQuizRepository.save(entity);
            return true;
        } catch (Exception e) {
            logger.error("UserDailyQuizManager save got exception", e);
            return false;
        }
    }
}
