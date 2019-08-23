package com.homecloud.backend.security;

import com.weissbeerger.dashboard.poc.backend.security.exceptions.NoAccessTokenException;
import com.weissbeerger.dashboard.poc.backend.security.models.AuthenticationDetails;
import com.weissbeerger.dashboard.poc.backend.security.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
public class AuthenticationByJwtFilter extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private String accessCookie;
    private String selectedRoleScopeCookie;
    private String localeCookie;
    private String identityRefreshUrl;

    public AuthenticationByJwtFilter(JwtUtils jwtUtils,
                                     String accessCookie,
                                     String selectedRoleScopeCookie,
                                     String localeCookie,
                                     String identityRefreshUrl) {
        this.jwtUtils = jwtUtils;
        this.accessCookie = accessCookie;
        this.selectedRoleScopeCookie = selectedRoleScopeCookie;
        this.localeCookie = localeCookie;
        this.identityRefreshUrl = identityRefreshUrl;
    }


    /**
     * Main function. This will be called at every request made to the server.
     * <p>
     * If the user has no permissions to the app, this filter will return an UNAUTHORIZED response.
     * If he has permissions but didnt select a roleScope, or selected a roleScope that doesnt belong to him,
     * he will continue as an anonymous user.
     * IF he has permissions and selected a valid roleScope,
     * he will proceed as an authenticated user with the selected roleScope
     *
     * @param request  The request object
     * @param response The responses to the client
     * @param chain    chain of filters
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = getUnsignedToken(request);

        try {
            if (token != null && !token.isEmpty()) {
                //Extract username and rolescopes from the token
                String username = jwtUtils.getUsernameFromToken(token);
                int userId = jwtUtils.getUserId(token);
                String locale = Locale.ENGLISH.toString();
                List<Integer> roleScopeIds = jwtUtils.getRoleScopeIdsFromToken(token);
                MDC.put("user_id", String.valueOf(userId));
                MDC.put("request_url", request.getRequestURL().toString());
                //Check if the user has a role to the app
                if (roleScopeIds != null && !roleScopeIds.isEmpty()) {

                    //The selected roleScope is the roleScope that the client passed in a cookie
                    //If he is entitled to that roleScope then he is authenticated.
                    //Else, dont set the authentication and continue as anonymous.
                    if (SelectedRoleScopeExists(request)) {
                        int selectedRoleScope = getSelectedRoleScope(request);
                        MDC.put("rolescope_id", String.valueOf(selectedRoleScope));
                        if (roleScopeIds.contains(selectedRoleScope)) {
                            log.info("Request arrived", selectedRoleScope);
                            LocalDateTime requestArrivedAt = LocalDateTime.now();
                            if (localeCookieExists(request)) {
                                locale = getlocaleFromCookie(request);
                            }
                            setAuthentication(username, selectedRoleScope, roleScopeIds, userId, locale, true);
                            chain.doFilter(request, response);
                            MDC.put("request_processing_time",
                                    String.valueOf(ChronoUnit.MILLIS.between(requestArrivedAt, LocalDateTime.now())));
                            log.info("Response sent");
                            return;
                        }
                    }
                    //User has at least one roleScopeId that matches the application
                    //but didnt select a roleScopeId or selected an invalid one.
                    //Continue as unauthenticated.
                    log.info("Setting user as unauthenticated.");
                    setAuthentication(username, -1, roleScopeIds, userId, locale, false);
                    chain.doFilter(request, response);
                    return;
                }
            }
            log.info("Unauthorized access to {}. No access cookie {} found", request.getRequestURL(), this.accessCookie);
            throw new NoAccessTokenException();
        }
        // Can only happen in local profile, because otherwise the api gateweay would have caught the expired token.
        // Redirect to identity for auto refresh
        catch (ExpiredJwtException | NoAccessTokenException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\n" +
                    "  \"redirect_url\": \"" + this.identityRefreshUrl + "\",\n" +
                    "  \"is_api_request\": true\n" +
                    "}");
        }
    }

    private boolean localeCookieExists(HttpServletRequest request) {
        Cookie localeCookie = WebUtils.getCookie(request, this.localeCookie);
        return (localeCookie != null);
    }

    private String getlocaleFromCookie(HttpServletRequest request) {
        return WebUtils.getCookie(request, localeCookie).getValue();

    }

    private boolean SelectedRoleScopeExists(HttpServletRequest request) {
        Cookie selectedRoleScopeCookie = WebUtils.getCookie(request, this.selectedRoleScopeCookie);
        return (selectedRoleScopeCookie != null && StringUtils.isNumeric(selectedRoleScopeCookie.getValue()));

    }

    private int getSelectedRoleScope(HttpServletRequest request) {
        return Integer.parseInt(WebUtils.getCookie(request, selectedRoleScopeCookie).getValue());

    }

    private void setAuthentication(String username, int selectedRoleScope, List<Integer> allRoleScopeIds, int userId, String locale, boolean authenticated) {
        AuthenticationDetails authenticationDetails = new AuthenticationDetails(allRoleScopeIds, selectedRoleScope, userId, locale);
        User user = new User(username, "", new ArrayList<>());
        List<GrantedAuthority> authorities;

        // Even though an unauthenticated user should be set as "unauthenticated" by "authentication.setAuthenticated(false)",
        // we keep it with default "authenticated" state ("authentication.isAuthenticated()" == true)
        // due to an inaccurate behavior we had when trying to set it as "unauthenticated".
        // From that reason, to differ between these states, an authenticated user gets an "AUTHENTICATED" role, while an unauthenticated user gets an "ANONYMOUS" role.
        if (authenticated) {
            authorities = AuthorityUtils.createAuthorityList("ROLE_AUTHENTICATED");
        } else {
            authorities = AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        authentication.setDetails(authenticationDetails);
        log.debug("setting security context for authorized user {}", username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * This function extracts the jwt from the cookies and removes the signature part.
     * Since we dont have the public key, we will be using the token without the signature part,
     * otherwise the jwtParser wont parse the token
     *
     * @param request The request object
     * @return The token without its signature
     */
    private String getUnsignedToken(HttpServletRequest request) {
        Cookie accessCookie = WebUtils.getCookie(request, this.accessCookie);
        if (accessCookie == null) {
            return null;
        }
        String signedToken = accessCookie.getValue();
        int i = signedToken.lastIndexOf('.');
        return signedToken.substring(0, i + 1);
    }
}
