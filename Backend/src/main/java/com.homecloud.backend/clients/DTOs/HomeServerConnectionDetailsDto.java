package com.homecloud.backend.clients.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeServerConnectionDetailsDto {

  @JsonProperty("username")
  private String username;

  @JsonProperty("home_server_address")
  private String homeServerAddress;

  @JsonProperty("home_server_name")
  private String homeServerName;

}
