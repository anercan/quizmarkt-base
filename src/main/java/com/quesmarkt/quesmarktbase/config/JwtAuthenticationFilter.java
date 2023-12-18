package com.quesmarkt.quesmarktbase.config;

import com.quesmarkt.quesmarktbase.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.quesmarkt.quesmarktbase.util.JwtUtil.getJwtFromRequest;

@WebFilter
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.isNotEmpty(jwt) && JwtUtil.checkJWT(jwt)) {
                UserContextHolder.createUserContextThreadLocal(JwtUtil.getUserId(jwt));
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                UserContextHolder.clear();
            }
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            UserContextHolder.clear();
        }
    }
}