package io.homecloud.homeserver.security;

import io.homecloud.homeserver.security.exceptions.NoAccessTokenException;
import io.homecloud.homeserver.security.models.AuthenticationDetails;
import io.homecloud.homeserver.security.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
public class AuthenticationByJwtFilter extends OncePerRequestFilter {
	private static final boolean debug = true;
	private JwtUtils jwtUtils;
	private final String ACCESS_COOCKIE_NAME = "access_cookie";
	private final String REFRESH_TOKEN_URL = "";
	private final String[] JWKs =
		{"JU/xcxir19xop/pRxbQ1ulSujIZPr03vr9ggNPw8+dg=", "imO/l2pDmgo61A7Cb6OPBTEdkTvNCFhoiOuAViPF12o="};

	public AuthenticationByJwtFilter(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String accessToken = getTokenFromCookie(request);
		if(debug) {
			setAuthentication("test", "test", true);
			chain.doFilter(request, response);
			log.info("Response sent");
			return;
		}
		try {
			if (accessToken == null || accessToken.isEmpty()) {
				log.info("JWT filter error: corrupted access token.");
				throw new NoAccessTokenException();
			}

			String kid = jwtUtils.getKid(accessToken);
			if (!Arrays.asList(this.JWKs).contains(kid)) {
				log.info("JWT filter error: invalid access token.");
				throw new NoAccessTokenException();
			}

			// Access token is valid.
			String username = jwtUtils.getUsername(accessToken);
			String userId = jwtUtils.getUserId(accessToken);
			setAuthentication(userId, username, true);
			chain.doFilter(request, response);
			log.info("Response sent");
		} catch (ExpiredJwtException | NoAccessTokenException e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write(e.getMessage());
		}
	}

	private void setAuthentication(String userId, String username, boolean authenticated) {
		AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, username);
		User user = new User(username, "", new ArrayList<>());
		List<GrantedAuthority> authorities;

		if (authenticated) {
			authorities = AuthorityUtils.createAuthorityList("ROLE_AUTHENTICATED");
		} else {
			authorities = AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS");
		}

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(user, null, authorities);
		authentication.setDetails(authenticationDetails);
		log.debug("setting security context for authorized user {}", username);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String getTokenFromCookie(HttpServletRequest request) {
		try {
			String accessCookie = request.getHeader("authorization").substring(7);
			int i = accessCookie.lastIndexOf('.');
			return accessCookie.substring(0, i + 1);
		}
		catch (NullPointerException e) {
			return null;
		}

	}
}
