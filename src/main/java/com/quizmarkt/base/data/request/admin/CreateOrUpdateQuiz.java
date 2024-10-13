package com.quizmarkt.base.data.request.admin;

import com.quizmarkt.base.data.enums.PremiumType;
import lombok.Data;

import java.util.List;

/**
 * @author anercan
 */

@Data
public class CreateOrUpdateQuiz {
    private Long id;
    private String name;
    private String attributes;
    private int priority;
    private boolean active;
    private List<Long> quizGroupIds;
    private List<PremiumType> availablePremiumTypes;
    int appId = 1;
}
