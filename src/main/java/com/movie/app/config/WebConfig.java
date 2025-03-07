package com.movie.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration class that configures Spring MVC settings.
 * This class adds interceptors to the application's request processing pipeline.
 * In this case, it registers an {@link ApiKeyInterceptor} to validate API keys for all incoming requests.
 *
 * @see WebMvcConfigurer
 * @see ApiKeyInterceptor
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final ApiKeyInterceptor apiKeyInterceptor;

  /**
   * Constructor to inject the ApiKeyInterceptor dependency.
   *
   * @param apiKeyInterceptor The interceptor responsible for validating API keys.
   */
  public WebConfig(ApiKeyInterceptor apiKeyInterceptor) {
    this.apiKeyInterceptor = apiKeyInterceptor;
  }

  /**
   * Registers the {@link ApiKeyInterceptor} to intercept all incoming requests.
   * The interceptor will validate the API key for each request before processing the request in the controller.
   *
   * @param registry The registry used to add interceptors to the application.
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(apiKeyInterceptor)
        .addPathPatterns("/**");
  }
}