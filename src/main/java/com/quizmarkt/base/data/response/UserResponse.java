package com.quizmarkt.base.data.response;

import com.quizmarkt.base.data.enums.PremiumType;
import lombok.Data;

import java.time.ZonedDateTime;

/**
 * @author anercan
 */

@Data
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private ZonedDateTime createdDate;
    private String avatarUrl;
    private Integer appId;
    private PremiumInfo premiumInfo;

    @Data
    private static class PremiumInfo {
        private PremiumType premiumType;
        private Long expireDate;
        private String subscriptionId;
        private String purchaseToken;
    }
}
