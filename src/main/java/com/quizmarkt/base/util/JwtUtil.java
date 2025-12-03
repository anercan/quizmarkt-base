package com.quizmarkt.base.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JwtUtil {
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    public static final String USER_ID = "user-id";
    public static final String PREMIUM_TYPE = "premium-type";
    public static final String APP_ID = "app-id";
    private static final String JWT_SECRET = System.getenv("JWT_SECRET");

    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private static SecretKeySpec getKey() {
        return new SecretKeySpec(JWT_SECRET.getBytes(StandardCharsets.UTF_8), SIGNATURE_ALGORITHM.getJcaName());
    }

    public static Claims checkAndGetJWTClaims(String jwt) {
        try {
            return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            log.warn("checkAndGetJWTClaims got expiredJwtException for jwt:{}", jwt);
            return null;
        } catch (Exception e) {
            log.error("checkAndGetJWTClaims got exception for jwt:{}", jwt, e);
            return null;
        }
    }
}