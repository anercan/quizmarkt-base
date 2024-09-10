package com.quizmarkt.base.data.context;

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
}
