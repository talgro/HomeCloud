package com.homecloud.backend.security.utils;

import com.homecloud.backend.security.models.AuthenticationDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

  public String getUsername(){
    AuthenticationDetails authenticationDetails =
      (AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    return authenticationDetails.getUsername();
  }

  public String getUserId(){
    AuthenticationDetails authenticationDetails =
      (AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    return authenticationDetails.getUserId();
  }

}
