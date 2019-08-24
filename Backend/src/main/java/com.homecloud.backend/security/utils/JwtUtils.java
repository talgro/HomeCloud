package com.homecloud.backend.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homecloud.backend.security.models.JwtTokenClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    public String getKey(String token) {
        return Jwts.jwsHeader().getKeyId();
    }

    public String getUsername(String token) {
        return getAllClaims(token).getEmail();
    }

    public String getUserId(String token) {
        return getAllClaims(token).getSub();
    }

    public String getUserFirstName(String token) {
        return getAllClaims(token).getGiven_name();
    }

    public String getUserLastName(String token) {
        return getAllClaims(token).getFamily_name();
    }

    private JwtTokenClaims getAllClaims(String token) {
        final ObjectMapper mapper = new ObjectMapper();
        Claims claims = Jwts.parser().parseClaimsJwt(token).getBody();
        return mapper.convertValue(claims, JwtTokenClaims.class);
    }
}
