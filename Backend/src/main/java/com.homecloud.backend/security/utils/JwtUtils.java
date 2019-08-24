package com.homecloud.backend.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homecloud.backend.security.models.JwtTokenClaims;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private JwtTokenClaims getAllClaims(String token) {
        final ObjectMapper mapper = new ObjectMapper();
        Claims claims = Jwts.parser().parseClaimsJwt(token).getBody();
        return mapper.convertValue(claims, JwtTokenClaims.class);
    }

    /**
     * @param token The unsigned token from passed from the api gateway
     * @return The roleScopeIds that are mapped to this application.
     * Or null if there is no mapping for this app in the token
     */
    public List<Integer> getRoleScopeIdsFromToken(String token) {
        String subject = Jwts.parser().parseClaimsJwt(token).getBody().getSubject();
        JSONObject subjectJson = new JSONObject(subject);

        //No roleScopeIds mapped to this app in the token
        if (!subjectJson.has(appName)) {
            return null;
        }

        //Parse the roleScopeIds to a list of integers and return them
        List<Integer> roleScopeIds = subjectJson.
                getJSONArray(appName).
                toList().
                stream().
                map(object -> Integer.parseInt(Objects.toString(object))).
                collect(Collectors.toList());

        return roleScopeIds;
    }

    public int getUserId(String token) {
        return Integer.parseInt(Jwts.parser().parseClaimsJwt(token).getBody().get("jti", String.class));
    }

}
