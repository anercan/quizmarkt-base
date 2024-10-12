package com.quizmarkt.base.data.request;

import com.quizmarkt.base.data.enums.PremiumType;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * @author anercan
 */

@Data
public class PremiumInfoRequest {
    private PremiumType premiumType;
    private ZonedDateTime expireDate;
    private String userId;
    private Long jwtExpireDate;
    private Map<String, String> jwtClaims;
    private int appId;
}
