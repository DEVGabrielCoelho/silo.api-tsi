package br.com.telematica.siloapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.AuthModel;
import br.com.telematica.siloapi.model.dto.ResponseAuthDTO;
import br.com.telematica.siloapi.model.dto.UsuarioDTO;
import br.com.telematica.siloapi.utils.Utils;

@Service
public class UsuarioServiceImpl extends UsuarioService {

	@Autowired
	protected AuthenticationManager authenticationManager;
	@Autowired
	protected AuthService authService;

	public ResponseAuthDTO authLogin(AuthModel authReq) throws Exception {
		UsuarioDTO userCheck = findLogin(authReq.getLogin());
		if (userCheck == null) {
			throw new RuntimeException("Usuário não existe!");
		}
		try {
			var userAutheticationToken = new UsernamePasswordAuthenticationToken(authReq.getLogin(), authReq.getSenha());
			authenticationManager.authenticate(userAutheticationToken);

			var token = authService.generateToken(authReq);
			// findUsernameAuth(userCheck.getCodigo(), userCheck.getPerfil().getPercod());
			return new ResponseAuthDTO(token, Utils.newDateString());
		} catch (AuthenticationException e) {
			throw new RuntimeException("Erro na autenticação: " + e.getMessage());
		}
	}

}
