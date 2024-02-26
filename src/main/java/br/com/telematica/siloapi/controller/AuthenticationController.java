package br.com.telematica.ciloapi.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.ciloapi.model.dto.AuthenticationDTO;
import br.com.telematica.ciloapi.model.dto.ResponseAuthenticationDTO;
import br.com.telematica.ciloapi.repository.AuthenticationRepository;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication Controller")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationRepository authService;

    
    @PostMapping("/authenticate")
    public ResponseAuthenticationDTO postMethodName(@RequestBody AuthenticationDTO entity) {

        var userAutheticationToken = new UsernamePasswordAuthenticationToken(entity.getLogin(), entity.getPassword());
        
        String role = userAutheticationToken.getAuthorities().toString();

        authenticationManager.authenticate(userAutheticationToken);

        return new ResponseAuthenticationDTO(authService.getToken(entity), role, new Date());
    }

}
