package com.quizmarkt.base.data.request;

import com.quizmarkt.base.data.enums.PremiumType;
import lombok.Data;

/**
 * @author anercan
 */
@Data
public class UserFilterRequest {
    private PremiumType premiumType;
    private String id;
}
