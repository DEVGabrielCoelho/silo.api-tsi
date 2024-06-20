package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.EmpresaModel;
import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import br.com.telematica.siloapi.services.EmpresaServInterface;
import br.com.telematica.siloapi.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("api/empresa")
@Tag(name = "Empresa", description = "API para controle e gerenciamento de empresas")
public class EmpresaController extends SecurityRestController {

	@Autowired
	private EmpresaServInterface empresaService;

	@GetMapping("/v1/paginado")
	@Operation(description = "Busca paginada de empresas. Fornece uma lista paginada de empresas com opções de filtragem e ordenação.")
	public ResponseEntity<Page<EmpresaDTO>> buscarEmpresaPaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho, @RequestParam(value = "filtro", required = false) String filtro,
			@RequestParam(value = "ordenarPor", defaultValue = "codigo") String ordenarPor, @RequestParam(value = "direcao", defaultValue = "ASC", required = false) @NonNull String direcao) throws IOException {
		String ordenarEntity = EmpresaDTO.consultaPagable(ordenarPor);
		if (ordenarEntity == null) {
			return ResponseEntity.badRequest().body(Page.empty());
		}
		return empresaService.empresaFindAllPaginado(filtro, Utils.consultaPage(ordenarEntity, direcao, pagina, tamanho));
	}

	@GetMapping("/v1/buscar/{codigo}")
	@Operation(description = "Buscar empresa pelo código. Retorna os detalhes de uma empresa específica com base no código fornecido.")
	public ResponseEntity<EmpresaDTO> buscarEmpresaPorCodigo(@PathVariable Long codigo) throws IOException {
		return empresaService.findByIdApi(codigo);
	}

	@GetMapping("/v1/buscar-cnpj/{cnpj}")
	@Operation(description = "Buscar empresa pelo CNPJ. Retorna os detalhes de uma empresa específica com base no CNPJ fornecido.")
	public ResponseEntity<EmpresaDTO> buscarEmpresaPorCnpj(@PathVariable Long cnpj) throws IOException {
		return empresaService.empresaFindByCnpjApi(cnpj);
	}

	@GetMapping("/v1/listar")
	@Operation(description = "Listar todas as empresas cadastradas. Retorna uma lista de todas as empresas existentes.")
	public ResponseEntity<List<EmpresaDTO>> buscarListaEmpresa() throws IOException {
		return empresaService.empresaFindAll();
	}

	@PostMapping("/v1/cadastrar")
	@Operation(description = "Cadastrar uma nova empresa. Recebe os detalhes da empresa e a armazena no sistema.")
	public ResponseEntity<EmpresaDTO> cadastrarEmpresa(@RequestBody EmpresaModel entity) throws IOException {
		return empresaService.empresaSave(entity);
	}

	@PutMapping("/v1/atualizar/{codigo}")
	@Operation(description = "Atualizar o cadastro de uma empresa. Atualiza os detalhes de uma empresa com base no código fornecido.")
	public ResponseEntity<EmpresaDTO> atualizarEmpresa(@PathVariable Long codigo, @RequestBody EmpresaModel entity) throws IOException {
		return empresaService.empresaUpdate(codigo, entity);
	}

	@DeleteMapping("/v1/deletar/{codigo}")
	@Operation(description = "Deletar uma empresa pelo código. Remove uma empresa específica com base no código fornecido.")
	public ResponseEntity<EmpresaDTO> deletarEmpresa(@PathVariable Long codigo) throws IOException {
		return empresaService.empresaDeleteById(codigo);
	}
}