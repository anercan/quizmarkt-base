package com.quizmarkt.base.service;

import com.quizmarkt.base.data.cache.QuizGroupCacheable;
import com.quizmarkt.base.data.mapper.QuizGroupMapper;
import com.quizmarkt.base.data.request.PageRequest;
import com.quizmarkt.base.data.request.QuizGroupRequest;
import com.quizmarkt.base.data.response.ApiResponse;
import com.quizmarkt.base.data.response.QuizGroupResponse;
import com.quizmarkt.base.data.response.QuizGroupWithUserData;
import com.quizmarkt.base.manager.QuizGroupManager;
import com.quizmarkt.base.manager.UserQuizManager;
import lombok.AllArgsConstructor;
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

    public ApiResponse<QuizGroupResponse> getQuizGroupsWithUserInfo(@RequestBody QuizGroupRequest request) {
        List<QuizGroupCacheable> quizGroupList = quizGroupManager.getActiveQuizGroups(PageRequest.builder().page(request.getPage()).pageSize(request.getPageSize()).build(), getAppId());
        Map<Long, Integer> userQuizMap = userQuizManager.getUserQuizGroupIdQuizCountMap(quizGroupList.stream().map(QuizGroupCacheable::getId).collect(Collectors.toSet()));
        List<QuizGroupWithUserData> quizGroupWithUserDataList = quizGroupList.stream().map(quizGroup -> quizGroupMapper.getQuizGroupWithUserData(userQuizMap, quizGroup)).collect(Collectors.toList());
        return new ApiResponse<>(QuizGroupResponse.builder().quizGroupWithUserDataList(quizGroupWithUserDataList).build());
    }
}
