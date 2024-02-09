package com.uniSystemAPI.domain.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

// import jakarta.servlet.http.HttpServletRequest;

@Component
public class ServerProperties {

  private static Environment environment;

  @Autowired
  public void setEnvironment(Environment environment) {
    ServerProperties.environment = environment;
  }

  public static String getServerAddress() {
     return environment.getProperty("uni.api.serverAddress").replace(" ", "");
   
    // HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
    //     .getRequest();
    // return getBaseUrl(request);
  }
  
  // private static String getBaseUrl(HttpServletRequest request) {
  //   // Obtenha a URL base
  //   StringBuffer requestURL = request.getRequestURL();
  //   System.out.println("requestURL "+requestURL);
  //   String requestURI = request.getRequestURI();
    
  //   System.out.println("requestURI "+requestURI);

  //   String baseUrl = requestURL.substring(0,
  //       requestURL.length() - requestURI.length() + request.getContextPath().length());

  //   return baseUrl;
  // }
}
