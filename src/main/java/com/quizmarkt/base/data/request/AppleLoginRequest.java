package com.quizmarkt.base.data.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppleLoginRequest extends SignInRequest {
    private String identityToken;
    private String appleUserId;
    private String fullName;
}