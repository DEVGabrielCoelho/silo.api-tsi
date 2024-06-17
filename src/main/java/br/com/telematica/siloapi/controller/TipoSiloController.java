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

import br.com.telematica.siloapi.model.TipoSiloModel;
import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import br.com.telematica.siloapi.services.TipoSiloInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tipo-silo")
@Tag(name = "Tipo Silo", description = "Tipo de Silo API")
public class TipoSiloController extends SecurityRestController {

	@Autowired
	private TipoSiloInterface tipoSiloInterface;

	@GetMapping("/v1")
	@Operation(description = "Busca pelos tipos silos cadastrados")
	public ResponseEntity<List<TipoSiloDTO>> getSilo() throws IOException {
		return tipoSiloInterface.findAllTipoSiloDTO();

	}

	@PostMapping("/v1")
	@Operation(description = "Cadastro de um novo tipo de silo")
	public ResponseEntity<TipoSiloDTO> createSilo(@Valid @RequestBody TipoSiloModel tipoSilo) throws RuntimeException, IOException {
		return tipoSiloInterface.save(tipoSilo);

	}

	@PutMapping("/v1/{codigo}")
	@Operation(description = "Atualização de um tipo de silo")
	public ResponseEntity<TipoSiloDTO> updateSilo(@Valid @PathVariable("codigo") Long codigo, @Valid @RequestBody TipoSiloModel tipoSilo) throws IOException {
		return tipoSiloInterface.update(codigo, tipoSilo);

	}

	@DeleteMapping("/v1/{codigo}")
	@Operation(description = "Deletar um Tipo de Silo")
	public ResponseEntity<TipoSiloDTO> deleteSilo(@Valid @PathVariable("codigo") Long codigo) throws IOException {
		return tipoSiloInterface.deleteByTsicod(codigo);
	}

}
