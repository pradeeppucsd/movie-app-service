package com.movie.app.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor for validating API key in incoming requests.
 */
@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

  /**
   * The valid API key that is expected in the request.
   * In this case, it is a hardcoded value for demonstration purposes.
   */
  private static final String API_KEY = "test_key";

  /**
   * Pre-handle method to validate the API key before processing the request.
   * If the API key is invalid or missing, the request is blocked and an error response
   * with HTTP status 401 (Unauthorized) is sent.
   *
   * @param request The incoming HTTP request.
   * @param response The outgoing HTTP response.
   * @param handler The handler (controller) that would process the request if validation passes.
   * @return {@code true} if the request should proceed to the handler; {@code false} if it should be blocked.
   * @throws Exception If an error occurs during API key validation.
   */
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
