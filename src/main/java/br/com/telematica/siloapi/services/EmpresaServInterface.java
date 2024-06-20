package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.EmpresaModel;
import br.com.telematica.siloapi.model.dto.EmpresaDTO;

public interface EmpresaServInterface {

	ResponseEntity<EmpresaDTO> empresaDeleteById(Long codigo) throws IOException;

	ResponseEntity<Page<EmpresaDTO>> empresaFindAllPaginado(String nome, Pageable pageable) throws IOException;

	ResponseEntity<List<EmpresaDTO>> empresaFindAll() throws IOException;

	ResponseEntity<EmpresaDTO> empresaUpdate(Long codigo, EmpresaModel empresaModel) throws IOException;

	ResponseEntity<EmpresaDTO> empresaSave(EmpresaModel empresaModel) throws IOException;

	ResponseEntity<EmpresaDTO> findByIdApi(Long codigo) throws IOException;

	ResponseEntity<EmpresaDTO> empresaFindByCnpjApi(Long codigo) throws IOException;

}
