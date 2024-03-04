package br.com.telematica.siloapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import br.com.telematica.siloapi.model.interfaces.SecurityRestController;
import br.com.telematica.siloapi.service.TipoSiloService;
import br.com.telematica.siloapi.utils.error.GenericResponseModel;
import br.com.telematica.siloapi.utils.error.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tipo-silo")
@Tag(name = "Tipo Silo", description = "Tipo de Silo API")
public class TipoSiloController implements SecurityRestController {

	@Autowired
	private TipoSiloService tipoSiloService;

	@GetMapping("/buscarTiposSilos")
	@Operation(description = "Busca pelos tipos silos cadastrados")
	public ResponseEntity<GenericResponseModel> getSilo() {
		try {
			var entity = tipoSiloService.findAllTipoSiloDTO();
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/cadastraTipoSilo")
	@Operation(description = "Cadastro de um novo tipo de silo")
	public ResponseEntity<GenericResponseModel> createSilo(@Valid @RequestBody TipoSiloDTO tipoSiloDTO) {
		try {
			var entity = tipoSiloService.save(tipoSiloDTO);
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/atualizaSilo")
	@Operation(description = "Atualização de um tipo de silo")
	public ResponseEntity<GenericResponseModel> updateSilo(@Valid @RequestBody TipoSiloDTO tipoSiloDTO) {
		try {
			var entity = tipoSiloService.update(tipoSiloDTO);
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deletarTipoSilo/{codigo}")
	@Operation(description = "Deletar um Tipo de Silo")
	public ResponseEntity<GenericResponseModel> deleteSilo(@Valid @PathVariable("codigo") Integer codigo) {
		try {
			tipoSiloService.deleteByTsicod(codigo);
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, null), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
