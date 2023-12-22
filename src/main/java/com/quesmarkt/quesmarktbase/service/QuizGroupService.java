package com.quesmarkt.quesmarktbase.service;

import com.quesmarkt.quesmarktbase.data.entity.QuizGroup;
import com.quesmarkt.quesmarktbase.data.mapper.QuizGroupMapper;
import com.quesmarkt.quesmarktbase.data.request.QuizGroupRequest;
import com.quesmarkt.quesmarktbase.data.response.QuizGroupResponse;
import com.quesmarkt.quesmarktbase.data.response.QuizGroupWithUserData;
import com.quesmarkt.quesmarktbase.manager.QuizGroupManager;
import com.quesmarkt.quesmarktbase.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author anercan
 */

@Service
@AllArgsConstructor
public class QuizGroupService extends BaseService {

    private final QuizGroupManager quizGroupManager;
    private final QuizGroupMapper quizGroupMapper;
    private final UserQuizManager userQuizManager;

    public ResponseEntity<QuizGroupResponse> getQuizGroups(@RequestBody QuizGroupRequest request) {
        List<QuizGroup> quizGroupList = quizGroupManager.getQuizGroups(request);
        Map<Long, Integer> userQuizMap = userQuizManager.getUserQuizGroupIdQuizCountMap(quizGroupList.stream().map(QuizGroup::getId).collect(Collectors.toSet()));
        List<QuizGroupWithUserData> quizGroupWithUserDataList = quizGroupList.stream().map(quizGroup -> quizGroupMapper.getQuizGroupWithUserData(userQuizMap, quizGroup)).collect(Collectors.toList());
        return ResponseEntity.ok(QuizGroupResponse.builder().quizGroupWithUserDataList(quizGroupWithUserDataList).build());
    }

}
