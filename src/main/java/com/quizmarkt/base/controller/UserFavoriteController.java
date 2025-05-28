package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.request.AddOrRemoveFavoriteRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.FavoriteQuestionIdsResponse;
import com.quizmarkt.base.data.response.QuizResponse;
import com.quizmarkt.base.service.UserFavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author anercan
 */
@RestController
@AllArgsConstructor
@RequestMapping("/favorite")
public class UserFavoriteController extends BaseController {

    private final UserFavoriteService userFavoriteService;

    @GetMapping("/get-user-question-ids")
    public ResponseEntity<ApiResponse<FavoriteQuestionIdsResponse>> getUserFavoritesQuestionIds() {
        return respond(userFavoriteService.getUserFavoritesQuestionIds());
    }

    @GetMapping("/get-user-questions")
    public ResponseEntity<ApiResponse<QuizResponse>> getUserFavorites() {
        return respond(userFavoriteService.getUserFavoriteQuestions());
    }

    @PostMapping("/add-or-remove")
    public ResponseEntity<ApiResponse<FavoriteQuestionIdsResponse>> addOrRemove(@RequestBody AddOrRemoveFavoriteRequest request) {
        return respond(userFavoriteService.addOrRemoveFavorite(request));
    }

}
