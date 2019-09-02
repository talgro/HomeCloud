package io.homecloud.homeserver.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterHomeServer {
	@JsonProperty("server_address")
	private String server_address;

	@JsonProperty("server_name")
	private String server_name;
}
