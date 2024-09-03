package com.quizmarkt.base.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class JwtUtil {
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final String USERNAME = "username";
    private static final String USER_ID = "user-id";
    private static final String APP_ID = "app-id";
    private static final String JWT_SECRET = System.getProperty("JWT_SECRET");

    public static boolean checkJWT(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parse(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private static SecretKeySpec getKey() {
        return new SecretKeySpec(getJwtSecret().getBytes(StandardCharsets.UTF_8), SIGNATURE_ALGORITHM.getJcaName());
    }

    public static String getJwtSecret() {
        return StringUtils.isEmpty(JWT_SECRET) ? "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N" : JWT_SECRET;
    }

    public static Long getUserId(String jwt) {
        try {
            String userId = getClaimWithKey(jwt, USER_ID);
            if (StringUtils.isNotEmpty(userId)) {
                return Long.parseLong(userId);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /*public static String getAppId(String jwt) {
        return getClaimWithKey(jwt, APP_ID);
    }*/

    private static String getClaimWithKey(String jwt, String key) {
        try {
            return (String) Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build().parseClaimsJws(jwt).getBody().get(key);
        } catch (Exception e) {
            return null;
        }
    }
}