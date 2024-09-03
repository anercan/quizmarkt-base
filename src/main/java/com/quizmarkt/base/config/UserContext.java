package com.quizmarkt.base.config;

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
    private Long userId;
    private int appId;
}
