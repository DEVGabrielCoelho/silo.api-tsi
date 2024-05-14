package br.com.telematica.siloapi.services;

import java.text.ParseException;
import java.util.List;

import br.com.telematica.siloapi.model.PermissaoModel;
import br.com.telematica.siloapi.model.dto.PermissaoDTO;
import br.com.telematica.siloapi.model.dto.Permissao_UsuarioDTO;
import br.com.telematica.siloapi.model.dto.UrlPermissoesDTO;

public interface PermissaoInterface {

	public List<PermissaoDTO> findByUsucod(Long cod, Long perfil) throws ParseException;

	public PermissaoDTO save(Long codiUser, Long codiPerfil, PermissaoModel dto);

	public List<PermissaoDTO> getAll();

	public List<Permissao_UsuarioDTO> getAllPermissao_UsuarioDTO(Long cod);

	public boolean delete(List<PermissaoDTO> perm);

	public UrlPermissoesDTO getPermissionsForRole(String role);

}
