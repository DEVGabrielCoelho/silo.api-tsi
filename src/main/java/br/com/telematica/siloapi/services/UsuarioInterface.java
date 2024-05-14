package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.AuthModel;
import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.UsuarioModel;
import br.com.telematica.siloapi.model.dto.UsuarioDTO;
import jakarta.transaction.Transactional;

public interface UsuarioInterface {

	public ResponseEntity<GenericResponseModel> authLogin(AuthModel authReq) throws IOException;

	public ResponseEntity<GenericResponseModel> findUserPermiAll();

	public ResponseEntity<GenericResponseModel> findUserPermiById(Long cod);

	@Transactional
	public ResponseEntity<GenericResponseModel> saveUserEncodePassword(Optional<UsuarioModel> userCadastro);

	public ResponseEntity<GenericResponseModel> deleteByCode(Long codigo);

	public Page<UsuarioDTO> usuarioFindAllPaginado(String nome, Pageable pageable);

	public ResponseEntity<GenericResponseModel> findAll() throws ParseException;

	public ResponseEntity<GenericResponseModel> findById(Long codigo) throws ParseException;

	@Transactional
	public ResponseEntity<GenericResponseModel> update(Long codigo, Optional<UsuarioModel> userCadastro) throws ParseException;
}
