package com.homecloud.backend.clients.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerNewAddressDto {

  @JsonProperty("server_address")
  private String serverAddress;

}
