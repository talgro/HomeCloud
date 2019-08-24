package com.homecloud.backend.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenClaims {
  private String sub; // user id given by cognito
  private String email_verified;
  private String iss;
  private String given_name;
  private String family_name;
  private String email;
  private String aud;
  private String exp;
}
