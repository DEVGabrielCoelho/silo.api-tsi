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
import br.com.telematica.siloapi.service.SiloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/silo")
@Tag(name = "Silo", description = "Silo API")
public class SiloController extends SecurityRestController {

	@Autowired
	private SiloService siloService;

	@GetMapping("/buscarSilo")
	@Operation(description = "Busca pelos silos cadastrados")
	public ResponseEntity<GenericResponseModel> getSilo() throws IOException {
		return siloService.findAllSiloDTO();

	}

	@PostMapping("/cadastraSilo")
	@Operation(description = "Cadastro de um novo silo")
	public ResponseEntity<GenericResponseModel> createSilo(@Valid @RequestBody SiloDTO siloDTO) {
		return siloService.save(siloDTO);
	}

	@PutMapping("/atualizaSilo")
	@Operation(description = "Atualização de um silo")
	public ResponseEntity<GenericResponseModel> updateSilo(@Valid @RequestBody SiloDTO siloDTO) throws IOException {
		return siloService.update(siloDTO);

	}

	@DeleteMapping("/deletarSilo/{codigo}")
	@Operation(description = "Deletar um silo")
	public ResponseEntity<GenericResponseModel> deleteSilo(@Valid @PathVariable("codigo") Integer codigo) throws IOException {
		return siloService.deleteByPlacod(codigo);

	}

}
