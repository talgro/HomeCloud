package com.homecloud.backend.clients.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersServersDto {

  @JsonProperty("user_id")
  private String userId;

  @JsonProperty("home_server_id")
  private String homeServerId;

}
