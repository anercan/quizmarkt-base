package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.entity.UserFavorites;
import com.quizmarkt.base.data.repository.UserFavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author anercan
 */

@RequiredArgsConstructor
@Service
public class UserFavoriteManager extends BaseManager {

    public static final int FAV_LIMIT = 4;
    private final UserFavoriteRepository userFavoriteRepository;

    public Set<Long> getUserFavoriteQuestionIds() {
        Optional<UserFavorites> favoriteOpt = userFavoriteRepository.findByUserId(getUserId());
        return favoriteOpt.map(userFavorites -> new HashSet<>(userFavorites.getQuestionIds())).orElse(new HashSet<>());
    }

    public void addToFavorite(Long questionId) {
        Optional<UserFavorites> favoriteOpt = userFavoriteRepository.findByUserId(getUserId());
        if (favoriteOpt.isPresent()) {
            UserFavorites userFavorites = favoriteOpt.get();
            List<Long> questionIds = userFavorites.getQuestionIds();
            if (userFavorites.getQuestionIds().size() >= FAV_LIMIT) {
                questionIds.remove(0);
            }
            questionIds.add(questionId);
            userFavorites.setLastModifiedDate(ZonedDateTime.now());
            userFavoriteRepository.save(userFavorites);
        } else {
            UserFavorites userFavorites = new UserFavorites();
            userFavorites.setUserId(getUserId());
            userFavorites.setLastModifiedDate(ZonedDateTime.now());
            userFavorites.setQuestionIds(List.of(questionId));
            userFavoriteRepository.save(userFavorites);
        }
    }

    public void removeFavorite(Long questionId) {
        Optional<UserFavorites> favoriteOpt = userFavoriteRepository.findByUserId(getUserId());
        if (favoriteOpt.isEmpty()) {
            logger.warn("User Favorite should be exist for this proccess user:{} questionId:{}", getUserId(), questionId);
        } else {
            UserFavorites userFavorites = new UserFavorites();
            userFavorites.getQuestionIds().remove(questionId);
            userFavoriteRepository.save(userFavorites);
        }
    }
}
