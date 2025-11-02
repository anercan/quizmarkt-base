package com.quizmarkt.base.data.context;

import com.quizmarkt.base.data.enums.PremiumType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author anercan
 */

@Getter
@Setter
@AllArgsConstructor
public class UserContext {
    private String userId;
    private int appId;
    private PremiumType premiumType;

    public UserContext(Integer appId) {
        this.appId = appId;
    }
}
