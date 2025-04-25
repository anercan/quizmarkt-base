package com.quizmarkt.base.controller;

import com.quizmarkt.base.data.request.AddOrRemoveFavoriteRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.QuestionResponse;
import com.quizmarkt.base.service.UserFavoriteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author anercan
 */
@RestController
@AllArgsConstructor
@RequestMapping("/favorite")
public class UserFavoriteController extends BaseController {

    private final UserFavoriteService userFavoriteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getUserFavorites() {
        return respond(userFavoriteService.getUserFavorites());
    }

    @PostMapping("add-or-remove")
    public ResponseEntity<ApiResponse<Boolean>> addOrRemove(@RequestBody AddOrRemoveFavoriteRequest request) {
        return respond(userFavoriteService.addOrRemoveFavorite(request));
    }

}
