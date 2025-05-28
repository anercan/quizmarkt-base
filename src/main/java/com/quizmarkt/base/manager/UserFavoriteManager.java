package com.quizmarkt.base.manager;

import com.quizmarkt.base.data.entity.UserFavorites;
import com.quizmarkt.base.data.repository.UserFavoriteRepository;
import com.quizmarkt.base.data.response.FavoriteQuestionIdsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author anercan
 */

@RequiredArgsConstructor
@Service
public class UserFavoriteManager extends BaseManager {

    public static final int FAV_LIMIT = 24;
    private final UserFavoriteRepository userFavoriteRepository;

    public List<Long> getUserFavoriteQuestionIds() {
        Optional<UserFavorites> favoriteOpt = userFavoriteRepository.findByUserId(getUserId());
        if (favoriteOpt.isPresent()) {
            return favoriteOpt.get().getQuestionIds();
        } else {
            return Collections.emptyList();
        }
    }

    public FavoriteQuestionIdsResponse addToFavorite(Long questionId) {
        try {
            Optional<UserFavorites> favoriteOpt = userFavoriteRepository.findByUserId(getUserId());
            if (favoriteOpt.isPresent()) {
                UserFavorites userFavorites = favoriteOpt.get();
                List<Long> questionIds = userFavorites.getQuestionIds();
                if (questionId == null) {
                    return FavoriteQuestionIdsResponse.builder().favoriteIds(questionIds).build();
                }
                if (questionIds.contains(questionId)) {
                    FavoriteQuestionIdsResponse.builder().favoriteIds(questionIds).build();
                }
                if (userFavorites.getQuestionIds().size() >= FAV_LIMIT) {
                    questionIds.remove(0);
                }
                questionIds.add(questionId);
                userFavorites.setLastModifiedDate(ZonedDateTime.now());
                UserFavorites saved = userFavoriteRepository.save(userFavorites);
                return FavoriteQuestionIdsResponse.builder().favoriteIds(saved.getQuestionIds()).build();
            } else {
                if (questionId == null) {
                    return FavoriteQuestionIdsResponse.builder().favoriteIds(Collections.emptyList()).build();
                }
                UserFavorites userFavorites = new UserFavorites();
                userFavorites.setUserId(getUserId());
                userFavorites.setLastModifiedDate(ZonedDateTime.now());
                userFavorites.setQuestionIds(List.of(questionId));
                UserFavorites saved = userFavoriteRepository.save(userFavorites);
                return FavoriteQuestionIdsResponse.builder().favoriteIds(saved.getQuestionIds()).build();
            }
        } catch (Exception e) {
            logger.error("addToFavorite operation got exception questionId:{} userID:{}", questionId, getUserId(), e);
            return FavoriteQuestionIdsResponse.builder().favoriteIds(Collections.emptyList()).build();
        }
    }

    public FavoriteQuestionIdsResponse removeFavorite(Long questionId) {
        Optional<UserFavorites> favoriteOpt = userFavoriteRepository.findByUserId(getUserId());
        if (favoriteOpt.isEmpty()) {
            logger.warn("User Favorite should be exist for this proccess user:{} questionId:{}", getUserId(), questionId);
            return FavoriteQuestionIdsResponse.builder().favoriteIds(Collections.emptyList()).build();
        } else {
            UserFavorites userFavorites = favoriteOpt.get();
            userFavorites.getQuestionIds().remove(questionId);
            UserFavorites saved = userFavoriteRepository.save(userFavorites);
            return FavoriteQuestionIdsResponse.builder().favoriteIds(saved.getQuestionIds()).build();
        }
    }
}
