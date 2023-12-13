package com.quesmarkt.quesmarktbase.service;

import com.quesmarkt.quesmarktbase.data.entity.UserQuiz;
import com.quesmarkt.quesmarktbase.data.mapper.UserQuizMapper;
import com.quesmarkt.quesmarktbase.data.response.UserQuizResponse;
import com.quesmarkt.quesmarktbase.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class UserQuizService {

    private final UserQuizManager userQuizManager;
    private final UserQuizMapper userQuizMapper;

    public ResponseEntity<List<UserQuizResponse>> getUserQuizList() {
        List<UserQuiz> userQuizList = userQuizManager.getUserQuizList();
        List<UserQuizResponse> userQuizResponseList = userQuizList.stream().map(userQuizMapper::toUserQuizResponse).collect(Collectors.toList());
        return ResponseEntity.ok(userQuizResponseList);
    }
}
