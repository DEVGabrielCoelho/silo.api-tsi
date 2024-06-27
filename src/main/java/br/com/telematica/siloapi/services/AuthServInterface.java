package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.com.telematica.siloapi.model.AuthModel;
import br.com.telematica.siloapi.model.dto.ResponseAuthDTO;
import br.com.telematica.siloapi.model.dto.TokenValidationResponseDTO;
import br.com.telematica.siloapi.records.GenerateTokenRecords;

public interface AuthServInterface extends UserDetailsService {
	public GenerateTokenRecords getToken(AuthModel authToken) throws IOException;

	public String validToken(String token);

	public Instant validateTimeToken(String token);

	ResponseEntity<ResponseAuthDTO> refreshToken(String token);

	ResponseEntity<TokenValidationResponseDTO> validateAndParseToken(@NonNull String token);

	ResponseEntity<ResponseAuthDTO> authLogin(@NonNull AuthModel authReq) throws AuthenticationException, IOException;
}
