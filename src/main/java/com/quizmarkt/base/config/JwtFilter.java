package com.quizmarkt.base.config;

import com.quizmarkt.base.data.context.UserContextHolder;
import com.quizmarkt.base.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.io.IOException;

import static com.quizmarkt.base.util.JwtUtil.getClaims;
import static com.quizmarkt.base.util.JwtUtil.getJwtFromRequest;

@Slf4j
public class JwtFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (HttpMethod.OPTIONS.matches(httpRequest.getMethod())) {
            respondOptions(httpResponse);
            return;
        }

        String path = httpRequest.getRequestURI();
        if (canSkipFilter(path)) {
            log.warn("JWT filter will skipped for req:{}", path);
            chain.doFilter(request, response);
            return;
        }

        try {
            String jwt = getJwtFromRequest(httpRequest);
            if (StringUtils.isNotEmpty(jwt) && JwtUtil.checkJWT(jwt)) {
                Claims claims = getClaims(jwt);
                if (claims == null) {
                    log.warn("UNAUTHORIZED operation couldn't get claims jwt:{}", jwt);
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    UserContextHolder.clear();
                    return;
                }
                UserContextHolder.createUserContextThreadLocal(claims);
                chain.doFilter(request, response);
            } else {
                log.warn("UNAUTHORIZED operation jwt:{}", jwt);
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                UserContextHolder.clear();
            }
        } catch (Exception ex) {
            log.warn("UNAUTHORIZED operation req:{}", path);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            UserContextHolder.clear();
        } finally {
            UserContextHolder.clear();
        }
    }

    private void respondOptions(HttpServletResponse httpResponse) {
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setStatus(HttpServletResponse.SC_OK);
    }

    private boolean canSkipFilter(String path) {
        return path.contains("google-sign-in") || path.contains("admin/login") || path.contains("swagger") || path.contains("api-docs");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}