package br.com.telematica.siloapi.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;

import br.com.telematica.siloapi.exception.CustomAccessDeniedHandler;
import br.com.telematica.siloapi.handler.PermissaoHandler;
import br.com.telematica.siloapi.handler.URLValidator;
import br.com.telematica.siloapi.records.TokenDeviceRecord;
import br.com.telematica.siloapi.services.impl.AuthServiceImpl;
import br.com.telematica.siloapi.services.impl.UsuarioServiceImpl;
import br.com.telematica.siloapi.utils.AuthDeviceUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

	private CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();

	@Autowired
	private AuthServiceImpl authRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	@Lazy
	private UsuarioServiceImpl userServImpl;

	@Autowired
	private PermissaoHandler permissionHandler;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
		try {
			TokenDeviceRecord token = this.recoverToken(request);
			String requestURI = request.getRequestURI();
			URLValidator validationResponse;
			if (token != null && token.device() != null) {
				UserDetails user = userDetailsService.loadUserByUsername("device");
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if (authentication == null || !authentication.isAuthenticated()) {
					accessDeniedHandler.handle(request, response, new AccessDeniedException("Access Denied"));
					return;
				}
				validationResponse = URLValidator.validateURL(requestURI);
				if (validationResponse == null || validationResponse.getRecursoMapEnum() == null) {
					accessDeniedHandler.handle(request, response, new AccessDeniedException("Access Denied"));
					return;
				}
				filterChain.doFilter(request, response);
				return;
			}

			if (token != null && token.token() != null) {
				String login = authRepository.validToken(token.token());
				UserDetails user = userDetailsService.loadUserByUsername(login);

				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

				if (authentication == null || !authentication.isAuthenticated()) {
					accessDeniedHandler.handle(request, response, new AccessDeniedException("Access Denied"));
					return;
				}

				String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null);

				validationResponse = URLValidator.validateURL(requestURI);
				if (validationResponse.getMessage().equals("URL inválida!") || validationResponse.getMessage().startsWith("Erro ao processar a URL!")) {
					accessDeniedHandler.handle(request, response, new AccessDeniedException("Invalid URL: " + validationResponse));
					return;
				}
				boolean hasPermission = permissionHandler.checkPermission(role, validationResponse, request.getMethod());
				if (!hasPermission) {
					accessDeniedHandler.handle(request, response, new AccessDeniedException("Not authorized to perform this action"));
					return;
				}
			}
			filterChain.doFilter(request, response);
		} catch (TokenExpiredException | AccessDeniedException e) {
			accessDeniedHandler.handle(request, response, new AccessDeniedException(e.getMessage()));
		}
	}

	private TokenDeviceRecord recoverToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null) {
			return null;
		}

		var tokenDevice = AuthDeviceUtil.validarTokenBaseReturn(authHeader.replace("Bearer ", ""));
		if (tokenDevice != null) {
			return new TokenDeviceRecord(null, tokenDevice.numeroSerie());
		}

		return new TokenDeviceRecord(authHeader.replace("Bearer ", ""), null);
	}
}
