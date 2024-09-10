package com.quizmarkt.base.config;

import com.quizmarkt.base.data.context.UserContextHolder;
import com.quizmarkt.base.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static com.quizmarkt.base.util.JwtUtil.getClaims;
import static com.quizmarkt.base.util.JwtUtil.getJwtFromRequest;

public class JwtFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        if (canSkipFilter(path)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String jwt = getJwtFromRequest(httpRequest);
            if (StringUtils.isNotEmpty(jwt) && JwtUtil.checkJWT(jwt)) {
                Claims claims = getClaims(jwt);
                if (claims == null) {
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    UserContextHolder.clear();
                    return;
                }
                UserContextHolder.createUserContextThreadLocal(claims);
                chain.doFilter(request, response);
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                UserContextHolder.clear();
            }
        } catch (Exception ex) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            UserContextHolder.clear();
        }
    }

    private boolean canSkipFilter(String path) {
        return path.contains("google-sign-in") || path.contains("start-app") || path.contains("/admin");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}