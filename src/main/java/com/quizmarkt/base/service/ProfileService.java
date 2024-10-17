package com.quizmarkt.base.service;

import com.quizmarkt.base.data.enums.UserQuizState;
import com.quizmarkt.base.data.response.UserDataResponse;
import com.quizmarkt.base.manager.QuizManager;
import com.quizmarkt.base.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class ProfileService extends BaseService {

    private final UserQuizManager userQuizManager;
    private final QuizManager quizManager;

    public ResponseEntity<UserDataResponse> getUserData() {
        if (isRegularPremium()) {
            UserDataResponse build = UserDataResponse.builder()
                    .totalQuizCount(quizManager.getActiveQuizCount())
                    .userSolvedQuizCount(userQuizManager.getUserQuizCount(UserQuizState.COMPLETED))
                    .userOngoingQuizCount(userQuizManager.getUserQuizCount(UserQuizState.ON_GOING))
                    .avatarUrl("https://lh3.googleusercontent.com/a/ACg8ocKd5hcKqsKsk1ol2I6auAMVewmw0AJzy16BMZQti4UIvE_a1g=s96-c") //todo
                    .build();
            return ResponseEntity.ok(build);
        } else {
            UserDataResponse build = UserDataResponse.builder()
                    .totalQuizCount(quizManager.getActiveQuizCount())
                    .userSolvedQuizCount(userQuizManager.getUserQuizCount(UserQuizState.COMPLETED))
                    .userOngoingQuizCount(userQuizManager.getUserQuizCount(UserQuizState.ON_GOING))
                    .avatarUrl("https://lh3.googleusercontent.com/a/ACg8ocKd5hcKqsKsk1ol2I6auAMVewmw0AJzy16BMZQti4UIvE_a1g=s96-c") //todo
                    .build();
            return ResponseEntity.ok(build);
        }
    }
}
