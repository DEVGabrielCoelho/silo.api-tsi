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
import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import br.com.telematica.siloapi.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/empresa")
@Tag(name = "Empresa", description = "Empresa API")
public class EmpresaController extends SecurityRestController {

	@Autowired
	private EmpresaService empServ;

	@GetMapping("/listaEmpresa")
	@Operation(description = "Busca pelas empresas cadastradas")
	public ResponseEntity<GenericResponseModel> getEmpresa() throws IOException {
		return empServ.findAllEmpresaDTO();
	}

	@PostMapping("/cadastraEmpresa")
	@Operation(description = "Cadastro de uma nova empresa")
	public ResponseEntity<GenericResponseModel> createEmpresa(@Valid @RequestBody EmpresaDTO empresa) {
		return empServ.salvar(empresa);
	}

	@PutMapping("/atualizaEmpresa")
	@Operation(description = "Atualização de uma empresa")
	public ResponseEntity<GenericResponseModel> updateEmpresa(@Valid @RequestBody EmpresaDTO empresa) throws IOException {
		return empServ.update(empresa);
	}

	@DeleteMapping("/deletaEmpresa/{codigo}")
	@Operation(description = "Deletar uma empresa")
	public ResponseEntity<GenericResponseModel> deleteEmpresa(@Valid @PathVariable Integer codigo) throws IOException {
		return empServ.deleteByEmpcod(codigo);
	}

}
