package com.quizmarkt.base.data.request;

import com.quizmarkt.base.data.enums.PremiumType;
import lombok.Data;

import java.util.Map;

/**
 * @author anercan
 */

@Data
public class PremiumInfoRequest {
    private PremiumType premiumType;
    private String userId;
    private Map<String, String> jwtClaims;
    private int appId;
    private GoogleSubscriptionRequest googleSubscriptionRequest;
}
