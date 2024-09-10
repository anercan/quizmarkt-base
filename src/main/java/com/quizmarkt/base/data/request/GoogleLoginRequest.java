package com.quizmarkt.base.data.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GoogleLoginRequest extends SignInRequest {
    private String token;
}