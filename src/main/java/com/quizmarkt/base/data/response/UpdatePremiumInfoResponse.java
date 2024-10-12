package com.quizmarkt.base.data.response;

import lombok.Data;

/**
 * @author anercan
 */

@Data
public class UpdatePremiumInfoResponse {
    private boolean isSucceed;
    private String message;
    private String jwt;
}
