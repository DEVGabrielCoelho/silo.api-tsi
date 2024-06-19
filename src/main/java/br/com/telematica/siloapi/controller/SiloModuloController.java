package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.List;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/silo-modulo")
@Tag(name = "Silo Modulo", description = "Silo API")
public class SiloModuloController extends SecurityRestController {


	@GetMapping("/v1/buscarSilo")
	@Operation(description = "Busca pelos modulos do silos cadastrados")
	public ResponseEntity<List<Object>> getSiloModulo() throws IOException {
		return null;

	}

	@PostMapping("/v1/cadastraSilo")
	@Operation(description = "Cadastro de um novo modulo do silo")
	public ResponseEntity<Object> createSiloModulo(@RequestBody SiloModel siloDTO) throws IOException {
		return null;
	}

	@PutMapping("/v1/atualizaSilo/{codigo}")
	@Operation(description = "Atualização de um modulo do silo")
	public ResponseEntity<Object> updateSiloModulo(@PathVariable("codigo") Long codigo, @RequestBody SiloModel siloDTO) throws IOException {
		return null;

	}

	@DeleteMapping("/v1/deletarSilo/{codigo}")
	@Operation(description = "Deletar um modulo do silo")
	public ResponseEntity<Object> deleteSiloModulo(@PathVariable("codigo") Long codigo) throws IOException {
		return null;

	}

}
