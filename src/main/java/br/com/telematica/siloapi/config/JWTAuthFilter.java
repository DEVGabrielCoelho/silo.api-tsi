package br.com.telematica.siloapi.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.telematica.siloapi.model.dto.UrlPermissoesDTO;
import br.com.telematica.siloapi.repository.AuthRepository;
import br.com.telematica.siloapi.service.PermissaoService;
import br.com.telematica.siloapi.utils.message.CustomAccessDeniedHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

	@Autowired
	private AuthRepository authRepository;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	@Lazy
	private PermissaoService permissionService;
	@Autowired
	BuildProperties env;
	private AccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
		String token = this.recoverToken(request);
		if (token != null) {

			String login = authRepository.validateToken(token);
			UserDetails user = userDetailsService.loadUserByUsername(login);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (authentication == null || !authentication.isAuthenticated()) {
				filterChain.doFilter(request, response);
				return;
			}

			String role = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null);

			if (role != null) {
				UrlPermissoesDTO permissions = permissionService.getPermissionsForRole(role);
				if (permissions != null && !hasPermission1(permissions, request)) {
					accessDeniedHandler.handle(request, response, new AccessDeniedException("Access Denied"));
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	private String recoverToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null) {
			return null;
		}
		return authHeader.replace("Bearer ", "");
	}

	private boolean hasPermission1(UrlPermissoesDTO permissions, HttpServletRequest request) {
		String path = request.getRequestURI();
		String method = request.getMethod();

//		 System.out.println("Verificando permissões para o caminho: " + path + " com método: " + method);

		String[] urls;
		switch (method) {
		case "GET":
			urls = permissions.getUrlGet();
			break;
		case "POST":
			urls = permissions.getUrlPost();
			break;
		case "PUT":
			urls = permissions.getUrlPut();
			break;
		case "DELETE":
			urls = permissions.getUrlDelete();
			break;
		default:
			return false;
		}

		for (String url : urls) {
//			 System.out.println("Permitido: " + url);
			if (path.startsWith(url) && (path.equals(url) || path.startsWith(url))) {
				return true;
			}
		}
		return false;
	}
}
