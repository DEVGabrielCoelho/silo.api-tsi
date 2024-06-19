package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.SiloModel;
import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.services.SiloServInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/silo")
@Tag(name = "Silo", description = "API para gerenciamento de silos")
public class SiloController extends SecurityRestController {

	@Autowired
	private SiloServInterface silo;

	@GetMapping("/v1/listar")
	@Operation(description = "Busca pelos silos cadastrados. Retorna uma lista de todos os silos existentes no sistema.")
	public ResponseEntity<List<SiloDTO>> getSilo() throws IOException {
		return silo.findAllSiloDTO();
	}

	@PostMapping("/v1/cadastrar")
	@Operation(description = "Cadastro de um novo silo. Recebe os detalhes do silo e o armazena no sistema.")
	public ResponseEntity<SiloDTO> createSilo(@RequestBody SiloModel siloDTO) throws IOException {
		return silo.save(siloDTO);
	}

	@PutMapping("/v1/atualizar/{codigo}")
	@Operation(description = "Atualização de um silo existente. Atualiza os detalhes de um silo com base no código fornecido.")
	public ResponseEntity<SiloDTO> updateSilo(@PathVariable("codigo") Long codigo, @RequestBody SiloModel siloDTO) throws IOException {
		return silo.update(codigo, siloDTO);
	}

	@DeleteMapping("/v1/deletar/{codigo}")
	@Operation(description = "Deletar um silo pelo código. Remove um silo específico com base no código fornecido.")
	public ResponseEntity<SiloDTO> deleteSilo(@PathVariable("codigo") Long codigo) throws IOException {
		return silo.deleteByPlacod(codigo);
	}
}
