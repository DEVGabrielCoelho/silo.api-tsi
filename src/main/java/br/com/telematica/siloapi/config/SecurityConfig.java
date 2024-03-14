package br.com.telematica.siloapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.telematica.siloapi.utils.error.CustomAccessDeniedHandler;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableWebSecurity
@SecurityScheme(name = "bearerAuth", description = "JWT Authentication", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class SecurityConfig {

	private static final String[] WHITE_LIST_URL = { "/api/v1/management/register", "/api/v1/management/authenticate", "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**",
			"/swagger-ui.html" };
//	private static final String[] WHITE_LIST_URL_TESTE = { "/api/v1/empresa/**", "/api/v1/planta/**", "/api/v1/silo/**", "/api/v1/tipo-silo/**", "/api/v1/medicao/**" };

	@Autowired
	private ConfigProperties confProp;

	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		var authUrlList = confProp.AUTHENTICATION_LIST_URL();

		return http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(ex -> ex.accessDeniedHandler(new CustomAccessDeniedHandler()))
				.authorizeHttpRequests(requests -> requests
						.requestMatchers(WHITE_LIST_URL).permitAll()
						.requestMatchers(HttpMethod.POST, authUrlList).hasAnyAuthority(confProp.AUTH_POST())
						.requestMatchers(HttpMethod.GET, authUrlList).hasAnyAuthority(confProp.AUTH_GET())
						.requestMatchers(HttpMethod.PUT, authUrlList).hasAnyAuthority(confProp.AUTH_PUT())
						.requestMatchers(HttpMethod.DELETE, authUrlList).hasAnyAuthority(confProp.AUTH_DEL())
						.anyRequest().hasAuthority("ADMIN"))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
