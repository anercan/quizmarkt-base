package com.quizmarkt.base.data.context;

import com.quizmarkt.base.data.enums.PremiumType;
import com.quizmarkt.base.util.JwtUtil;
import io.jsonwebtoken.Claims;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<>();

    public static void createUserContextThreadLocal(Claims claims) {
        UserContext userContext = new UserContext((String) claims.get(JwtUtil.USER_ID)
                , (Integer) claims.get(JwtUtil.APP_ID)
                , PremiumType.valueOf((String) claims.get(JwtUtil.PREMIUM_TYPE)));
        userContextThreadLocal.set(userContext);
    }

    public static String getUserId() {
        if (userContextThreadLocal.get() != null) {
            return userContextThreadLocal.get().getUserId();
        }
        return null;
    }

    public static Integer getAppId() {
        if (userContextThreadLocal.get() != null) {
            return userContextThreadLocal.get().getAppId();
        }
        return null;
    }

    public static PremiumType getPremiumType() {
        if (userContextThreadLocal.get() != null) {
            return userContextThreadLocal.get().getPremiumType();
        }
        return null;
    }

    public static void clear() {
        userContextThreadLocal.remove();
    }
}