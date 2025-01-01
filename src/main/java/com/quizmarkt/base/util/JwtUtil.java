package com.quizmarkt.base.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class JwtUtil {
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    public static final String USER_ID = "user-id";
    public static final String PREMIUM_TYPE = "premium-type";
    public static final String APP_ID = "app-id";
    private static final String JWT_SECRET = System.getenv("JWT_SECRET");

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
        return new SecretKeySpec(JWT_SECRET.getBytes(StandardCharsets.UTF_8), SIGNATURE_ALGORITHM.getJcaName());
    }

    public static Claims getClaims(String jwt) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build().parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}