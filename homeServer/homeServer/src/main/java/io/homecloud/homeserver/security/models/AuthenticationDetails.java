package io.homecloud.homeserver.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AuthenticationDetails {
  private String userId;
  private String username;
}
