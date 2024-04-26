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

import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import br.com.telematica.siloapi.service.EmpresaService;
import br.com.telematica.siloapi.utils.error.GenericResponseModel;
import br.com.telematica.siloapi.utils.error.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/empresa")
@Tag(name = "Empresa", description = "Empresa API")
public class EmpresaController extends SecurityRestController {

	@Autowired
	private EmpresaService empresaService;

	@GetMapping("/listaEmpresa")
	@Operation(description = "Busca pelas empresas cadastradas")
	public ResponseEntity<GenericResponseModel> getEmpresa() {
		try {
			var entity = empresaService.findAllEmpresaDTO();
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/cadastraEmpresa")
	@Operation(description = "Cadastro de uma nova empresa")
	public ResponseEntity<GenericResponseModel> createEmpresa(@Valid @RequestBody EmpresaDTO empresa) {
		try {
			var entity = empresaService.salvar(empresa);
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/atualizaEmpresa")
	@Operation(description = "Atualização de uma empresa")
	public ResponseEntity<GenericResponseModel> updateEmpresa(@Valid @RequestBody EmpresaDTO empresa) {
		try {
			var entity = empresaService.update(empresa);
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deletaEmpresa/{codigo}")
	@Operation(description = "Deletar uma empresa")
	public ResponseEntity<GenericResponseModel> deleteEmpresa(@Valid @PathVariable Integer codigo) {
		try {
			empresaService.deleteByEmpcod(codigo);
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Deletado com Sucesso", null, null), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar a exclusão. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
