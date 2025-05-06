package com.quizmarkt.base.data.mapper;

import com.quizmarkt.base.data.entity.Answer;
import com.quizmarkt.base.data.entity.Question;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateAnswer;
import com.quizmarkt.base.data.request.admin.CreateOrUpdateQuestion;
import com.quizmarkt.base.data.response.QuestionResponse;
import com.quizmarkt.base.util.MapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author anercan
 */

@Mapper(componentModel = "spring")
public interface QuestionMapper {


    default Optional<Question> toQuestionEntity(CreateOrUpdateQuestion request, Optional<Question> optionalQuestion) {
        try {
            Question question = optionalQuestion.orElseGet(Question::new);
            question.setActive(request.isActive());
            question.setContent(request.getContent().trim());
            question.setImgUrl(request.getImgUrl());
            question.setPriority(request.getPriority());
            question.setExplanation(request.getExplanation());
            if (StringUtils.isNotEmpty(request.getAttributes())) {
                Map<String, String> attributeMap = MapperUtils.getAttributeMapFromString(request.getAttributes());
                if (attributeMap != null) {
                    question.setAttributes(attributeMap);
                }
            }
            if (!CollectionUtils.isEmpty(request.getCreateOrUpdateAnswerList())) {
                List<Answer> answerList = new ArrayList<>();
                Collections.shuffle(request.getCreateOrUpdateAnswerList());
                for (CreateOrUpdateAnswer answerReq : request.getCreateOrUpdateAnswerList()) {
                    Answer answer = new Answer();
                    if (answerReq.getId() != null) {
                        answer.setId(answerReq.getId());
                    }
                    answer.setContent(answerReq.getContent().trim());
                    answer.setImgUrl(answerReq.getImgUrl());
                    answerList.add(answer);
                }
                question.setAnswersList(answerList);
            }
            return Optional.of(question);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    List<QuestionResponse> toQuestionListResponse(List<Question> questionList);

}
