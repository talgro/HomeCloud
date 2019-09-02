package io.homecloud.synchronizedFolder.security;

import io.homecloud.synchronizedFolder.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	//  private List<String> allowedOrigins;
	private JwtUtils jwtUtils;

	@Autowired
	public WebSecurityConfig(JwtUtils jwtUtils
			//                           @Value("#{'${dashboard.cors.urls}'.split(',')}") List<String> allowedOrigins
			) {
		this.jwtUtils = jwtUtils;
		//    this.allowedOrigins = allowedOrigins;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// A filter responsible for setting authentication.
		AuthenticationByJwtFilter jwtAuthenticationFilter = new AuthenticationByJwtFilter(this.jwtUtils);

		httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		// The server is stateless so no need to enable csrf protection
		httpSecurity.cors().and().csrf().disable();

		// An unauthenticated user should only access these paths
		httpSecurity.authorizeRequests()
		.antMatchers("/clients", "/clients/**").hasAnyRole("ANONYMOUS", "AUTHENTICATED")
		.anyRequest().hasRole("AUTHENTICATED");
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/actuator/**","/SNSNotification");
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
