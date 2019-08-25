package com.homecloud.backend.clients.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizedPocDto {
  @JsonProperty("poc")
  PocDto poc;

  @JsonProperty("role_scope_id")
  private int roleScopeId;

  @JsonProperty("role_scope_permissions")
  private List<String> roleScopePermissions;
}
