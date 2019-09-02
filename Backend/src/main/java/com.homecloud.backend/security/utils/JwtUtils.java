package com.homecloud.backend.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homecloud.backend.security.models.JwtTokenClaims;
import com.homecloud.backend.security.models.JwtTokenHeader;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    public String getKid(String token) {
        JwtTokenHeader r =  getHeaderClaims(token);
        return r.getKid();
    }

    public String getUsername(String token) {
        return getAllClaims(token).getEmail();
    }

    public String getUserId(String token) {
        return getAllClaims(token).getSub();
    }

    private JwtTokenClaims getAllClaims(String token) {
        final ObjectMapper mapper = new ObjectMapper();
        Claims claims = Jwts.parser().parseClaimsJwt(token).getBody();
        JwtTokenClaims jwtTokenClaims = mapper.convertValue(claims, JwtTokenClaims.class);
        return jwtTokenClaims;
    }

    private JwtTokenHeader getHeaderClaims(String token) {
        final ObjectMapper mapper = new ObjectMapper();
        Header claims = Jwts.parser().parseClaimsJwt(token).getHeader();
        return mapper.convertValue(claims, JwtTokenHeader.class);
    }
}
