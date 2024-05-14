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
	private TipoSiloInterface tipoSilo;

	@GetMapping("/v1/buscarTiposSilos")
	@Operation(description = "Busca pelos tipos silos cadastrados")
	public ResponseEntity<GenericResponseModel> getSilo() throws IOException {
		return tipoSilo.findAllTipoSiloDTO();

	}

	@PostMapping("/v1/cadastraTipoSilo")
	@Operation(description = "Cadastro de um novo tipo de silo")
	public ResponseEntity<GenericResponseModel> createSilo(@Valid @RequestBody TipoSiloDTO tipoSiloDTO) {
		return tipoSilo.save(tipoSiloDTO);

	}

	@PutMapping("/v1/atualizaSilo")
	@Operation(description = "Atualização de um tipo de silo")
	public ResponseEntity<GenericResponseModel> updateSilo(@Valid @RequestBody TipoSiloDTO tipoSiloDTO) throws IOException {
		return tipoSilo.update(tipoSiloDTO);

	}

	@DeleteMapping("/v1/deletarTipoSilo/{codigo}")
	@Operation(description = "Deletar um Tipo de Silo")
	public ResponseEntity<GenericResponseModel> deleteSilo(@Valid @PathVariable("codigo") Integer codigo) throws IOException {
		return tipoSilo.deleteByTsicod(codigo);
	}

}
