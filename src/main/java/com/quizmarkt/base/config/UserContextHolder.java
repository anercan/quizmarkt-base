package com.quizmarkt.base.config;

public class UserContextHolder {

    private static final ThreadLocal<UserContext> userContextThreadLocal = new ThreadLocal<>();

    public static void createUserContextThreadLocal(Long userId) {
        userContextThreadLocal.set(new UserContext(userId, 1));
    }

    public static Long getUserId() {
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

    public static void clear() {
        userContextThreadLocal.remove();
    }
}