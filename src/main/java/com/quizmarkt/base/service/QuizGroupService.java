package com.quizmarkt.base.service;

import com.quizmarkt.base.data.entity.QuizGroup;
import com.quizmarkt.base.data.mapper.QuizGroupMapper;
import com.quizmarkt.base.data.repository.QuizGroupRepository;
import com.quizmarkt.base.data.request.CreateOrUpdateQuizGroup;
import com.quizmarkt.base.data.request.PageRequest;
import com.quizmarkt.base.data.request.QuizGroupRequest;
import com.quizmarkt.base.data.response.QuizGroupResponse;
import com.quizmarkt.base.data.response.QuizGroupWithUserData;
import com.quizmarkt.base.manager.QuizGroupManager;
import com.quizmarkt.base.manager.UserQuizManager;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private final QuizGroupRepository quizGroupRepository;

    public ResponseEntity<QuizGroupResponse> getQuizGroupsWithUserInfo(@RequestBody QuizGroupRequest request) {
        List<QuizGroup> quizGroupList = quizGroupManager.getActiveQuizGroups(PageRequest.builder().page(request.getPage()).pageSize(request.getPageSize()).build());
        Map<Long, Integer> userQuizMap = userQuizManager.getUserQuizGroupIdQuizCountMap(quizGroupList.stream().map(QuizGroup::getId).collect(Collectors.toSet()));
        List<QuizGroupWithUserData> quizGroupWithUserDataList = quizGroupList.stream().map(quizGroup -> quizGroupMapper.getQuizGroupWithUserData(userQuizMap, quizGroup)).collect(Collectors.toList());
        return ResponseEntity.ok(QuizGroupResponse.builder().quizGroupWithUserDataList(quizGroupWithUserDataList).build());
    }

    public ResponseEntity<List<QuizGroup>> getQuizGroupList(@RequestBody PageRequest request) {
        List<QuizGroup> quizGroupList = quizGroupManager.getQuizGroups(request);
        return ResponseEntity.ok(quizGroupList);
    }

    public ResponseEntity<Void> saveQuizGroup(CreateOrUpdateQuizGroup request) {
        Optional<QuizGroup> optionalQuizGroup = request.getId() != null ? quizGroupRepository.findById(request.getId()) : Optional.empty();
        Optional<QuizGroup> quizGroup = quizGroupMapper.toQuizGroupEntity(request, optionalQuizGroup);
        quizGroup.ifPresent(quizGroupManager::saveQuizGroup);
        return ResponseEntity.ok().build();
    }
}
