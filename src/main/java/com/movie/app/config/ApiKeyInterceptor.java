package com.movie.app.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {
  private static final String API_KEY = "test_key";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String apiKey = request.getParameter("api_key");
    if (apiKey == null || !apiKey.equals(API_KEY)) {
      response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid API Key");
      return false;
    }
    return true;
  }
}