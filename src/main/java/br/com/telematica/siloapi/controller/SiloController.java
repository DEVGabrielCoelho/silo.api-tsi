package br.com.telematica.siloapi.controller;

import java.io.IOException;

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

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.services.SiloInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/silo")
@Tag(name = "Silo", description = "Silo API")
public class SiloController extends SecurityRestController {

	@Autowired
	private SiloInterface silo;

	@GetMapping("/v1/buscarSilo")
	@Operation(description = "Busca pelos silos cadastrados")
	public ResponseEntity<GenericResponseModel> getSilo() throws IOException {
		return silo.findAllSiloDTO();

	}

	@PostMapping("/v1/cadastraSilo")
	@Operation(description = "Cadastro de um novo silo")
	public ResponseEntity<GenericResponseModel> createSilo(@Valid @RequestBody SiloDTO siloDTO) {
		return silo.save(siloDTO);
	}

	@PutMapping("/v1/atualizaSilo")
	@Operation(description = "Atualização de um silo")
	public ResponseEntity<GenericResponseModel> updateSilo(@Valid @RequestBody SiloDTO siloDTO) throws IOException {
		return silo.update(siloDTO);

	}

	@DeleteMapping("/v1/deletarSilo/{codigo}")
	@Operation(description = "Deletar um silo")
	public ResponseEntity<GenericResponseModel> deleteSilo(@Valid @PathVariable("codigo") Integer codigo) throws IOException {
		return silo.deleteByPlacod(codigo);

	}

}
