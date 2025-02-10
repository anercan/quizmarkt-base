package com.quizmarkt.base.config;

import com.quizmarkt.base.data.context.UserContextHolder;
import com.quizmarkt.base.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import static com.quizmarkt.base.util.JwtUtil.getClaims;
import static com.quizmarkt.base.util.JwtUtil.getJwtFromRequest;

@Slf4j
public class JwtFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();
        if (canSkipFilter(path)) {
            log.warn("JWT filter will skipped for req:{}", ((HttpServletRequest) request).getPathInfo());
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
            log.warn("UNAUTHORIZED operation req:{}", httpRequest);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            UserContextHolder.clear();
        }
    }

    private boolean canSkipFilter(String path) {
        return path.contains("google-sign-in") || path.contains("swagger") || path.contains("api-docs");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}