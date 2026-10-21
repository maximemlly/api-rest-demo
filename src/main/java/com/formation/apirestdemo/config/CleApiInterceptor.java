package com.formation.apirestdemo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CleApiInterceptor implements HandlerInterceptor {

    private static final String EN_TETE = "X-Api-Key";
    private static final String CLE_ATTENDUE = "formation";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        if ("DELETE".equals(request.getMethod())
                && !CLE_ATTENDUE.equals(request.getHeader(EN_TETE))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
