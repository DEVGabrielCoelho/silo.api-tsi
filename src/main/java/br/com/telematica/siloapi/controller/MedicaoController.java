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

import br.com.telematica.siloapi.model.dto.MedicaoDTO;
import br.com.telematica.siloapi.service.MedicaoService;
import br.com.telematica.siloapi.utils.error.GenericResponseModel;
import br.com.telematica.siloapi.utils.error.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/medicao")
@Tag(name = "Medições", description = "Medições API")
public class MedicaoController extends SecurityRestController {

	@Autowired
	private MedicaoService medicaoService;

	@GetMapping("/buscarMedicoes")
	@Operation(description = "Busca pelas medição registrada")
	public ResponseEntity<GenericResponseModel> getMedicao() {
		try {
			var entity = medicaoService.findAllMedicaoDTO();
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/cadastraMedicao")
	@Operation(description = "Cadastro de uma nova medição")
	public ResponseEntity<GenericResponseModel> createMedicao(@Valid @RequestBody MedicaoDTO medicaoDTO) {
		try {
			var entity = medicaoService.save(medicaoDTO);
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity), HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/atualizaMedicao")
	@Operation(description = "Atualização de uma medição")
	public ResponseEntity<GenericResponseModel> updateMedicao(@Valid @RequestBody MedicaoDTO medicaoDTO) {
		try {
			var entity = medicaoService.update(medicaoDTO);
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deletarMedicao/{dataMedicao}")
	@Operation(description = "Deletar uma medição")
	public ResponseEntity<GenericResponseModel> deleteMedicao(@Valid @PathVariable("dataMedicao") String dataMedicao) {
		try {
			medicaoService.deleteByMsidth(dataMedicao);
			return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, null), HttpStatus.OK);
		} catch (IOException e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(MessageResponse.exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
