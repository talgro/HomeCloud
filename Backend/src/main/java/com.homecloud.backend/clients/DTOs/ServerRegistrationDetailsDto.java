package com.homecloud.backend.clients.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerRegistrationDetailsDto {

  @JsonProperty("server_address")
  private String serverAddress;

  @JsonProperty("server_name")
  private String serverName;

}
