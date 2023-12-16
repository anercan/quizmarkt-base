package com.quesmarkt.quesmarktbase.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class JwtUtil {
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private static final String USERNAME = "username";
    private static final String USER_ID = "userId";
    private static final String REFRESH = "refresh";
    private static final String JWT_SECRET = System.getProperty("JWT_SECRET");

    private static long getSessionTime(Long days) {
        return days * 24 * 60 * 60 * 1000L;
    }

    private static Claims getClaims(String id, String username) {
        Claims claims = Jwts.claims();
        claims.put(USER_ID, id);
        claims.put(USERNAME, username);
        return claims;
    }

    public static boolean checkJWT(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parse(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static SecretKeySpec getKey() {
        return new SecretKeySpec(getJwtSecret().getBytes(StandardCharsets.UTF_8), SIGNATURE_ALGORITHM.getJcaName());
    }

    public static String extractUsername(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        try {
            return (String) Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build().parseClaimsJws(jwt).getBody().get(USERNAME);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getJwtSecret() {
        return StringUtils.isEmpty(JWT_SECRET) ? "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N" : JWT_SECRET;
    }
}