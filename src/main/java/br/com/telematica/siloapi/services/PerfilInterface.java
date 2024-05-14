package br.com.telematica.siloapi.services;

import java.util.List;

import org.springframework.lang.NonNull;

import br.com.telematica.siloapi.model.dto.PerfilDTO;
import br.com.telematica.siloapi.model.entity.PerfilEntity;

public interface PerfilInterface {

	public List<PerfilDTO> findAll();

	public PerfilDTO findById(@NonNull Long codigo);

	public PerfilEntity save(String desc);

	public boolean delete(@NonNull Long perfil);

}
