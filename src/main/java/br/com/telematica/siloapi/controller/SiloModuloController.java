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
@Tag(name = "Silo Módulo", description = "API para gerenciamento de módulos de silos")
public class SiloModuloController extends SecurityRestController {

	@GetMapping("/v1/buscar")
	@Operation(description = "Busca pelos módulos dos silos cadastrados. Retorna uma lista de todos os módulos de silos existentes.")
	public ResponseEntity<List<Object>> getSiloModulo() throws IOException {
		return null; // Implementar lógica de busca
	}

	@PostMapping("/v1/cadastrar")
	@Operation(description = "Cadastro de um novo módulo de silo. Recebe os detalhes do módulo e o armazena no sistema.")
	public ResponseEntity<Object> createSiloModulo(@RequestBody SiloModel siloDTO) throws IOException {
		return null; // Implementar lógica de cadastro
	}

	@PutMapping("/v1/atualizar/{codigo}")
	@Operation(description = "Atualização de um módulo de silo existente. Atualiza os detalhes de um módulo com base no código fornecido.")
	public ResponseEntity<Object> updateSiloModulo(@PathVariable("codigo") Long codigo, @RequestBody SiloModel siloDTO) throws IOException {
		return null; // Implementar lógica de atualização
	}

	@DeleteMapping("/v1/deletar/{codigo}")
	@Operation(description = "Deletar um módulo de silo pelo código. Remove um módulo específico com base no código fornecido.")
	public ResponseEntity<Object> deleteSiloModulo(@PathVariable("codigo") Long codigo) throws IOException {
		return null; // Implementar lógica de exclusão
	}
}
