package com.homecloud.backend.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenClaims {
  private String sub; // "sub"
  private String event_id;
  private String token_use;
  private String scope;
  private long auth_time;
  private String iss;
  private long exp;
  private long iat;
  private String jti;
  private String client_id;
  private String username;
}
