package com.homecloud.backend.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenClaims {
  private String sub; // user id given by cognito
  private boolean email_verified;
  private String iss;
  private String given_name;
  private String aud;
  private String event_id;
  private String token_use;
  private long auth_time;
  private long exp;
  private long iat;
  private String family_name;
  private String email;
}
