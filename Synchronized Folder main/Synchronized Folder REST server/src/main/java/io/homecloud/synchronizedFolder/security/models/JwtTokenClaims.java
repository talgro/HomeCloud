package io.homecloud.synchronizedFolder.security.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenClaims {
	private String sub;
    private boolean email_verified;
    private String iss;
    @JsonProperty("cognito:username")
    private String username;
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
