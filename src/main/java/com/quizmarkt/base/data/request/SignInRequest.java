package com.quizmarkt.base.data.request;

import com.quizmarkt.base.data.enums.OSType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
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
    private Map<String, String> jwtClaims;
    private Date expirationDate;
    private int appId;
    private SignInType signInType;

    @Data
    public static class DeviceInfo {
        String token;
        OSType osType;
    }
}
