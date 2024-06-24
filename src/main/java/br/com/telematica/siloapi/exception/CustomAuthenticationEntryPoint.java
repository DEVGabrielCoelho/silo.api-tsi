package br.com.telematica.siloapi.exception;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.google.gson.Gson;

import br.com.telematica.siloapi.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		ResponseGlobalModel globalResponse = new ResponseGlobalModel(true, authException.getMessage(), Utils.convertDateToString(silomodulo.getSmohke()));
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write(new Gson().toJson(globalResponse));
	}

}
