package com.quizmarkt.base.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author anercan
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    private String email;
    private String password;
    private Map<String,String> jwtClaims;
    private Long expirationDays;
    private int appId;
    private SignInType signInType;
}
