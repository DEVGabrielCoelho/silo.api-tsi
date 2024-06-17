package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.text.ParseException;
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
import jakarta.persistence.EntityNotFoundException;

@CrossOrigin
@RestController
@RequestMapping("api/empresa")
@Tag(name = "Empresa", description = "API para controle de empresas")
public class EmpresaController extends SecurityRestController {

	@Autowired
	private EmpresaServInterface empresaService;

	@GetMapping("/v1/paginado")
	@Operation(description = "Busca paginada de Empresas cadastradas. Pagina define qual pagina deseja abrir, Tamanho define a quantidade e itens por pagina, Filtro permite buscar pelo código, nome, nome fantasia, cnpj e telefone, Direção definie a ordenação ASC - Ascendente / DESC - Descendente  OrdenarPor requer os seguintes dados: codigo, cnpj, nome, nomeFantasia, telefone.")
	public ResponseEntity<Page<EmpresaDTO>> buscarEmpresaPaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho, @RequestParam(value = "filtro", required = false) String filtro,
			@RequestParam(value = "ordenarPor", defaultValue = "codigo") String ordenarPor, @RequestParam(value = "direcao", defaultValue = "ASC", required = false) @NonNull String direcao) throws IOException {
		String ordenarEntity = EmpresaDTO.consultaPagable(ordenarPor);
		if (ordenarEntity == null) {
			return ResponseEntity.badRequest().body(Page.empty());
		}
		return empresaService.empresaFindAllPaginado(filtro, Utils.consultaPage(ordenarEntity, direcao, pagina, tamanho));
	}

	@GetMapping("/v1/codigo/{codigo}")
	@Operation(description = "Buscar empresa pelo código cadastrado.")
	public ResponseEntity<EmpresaDTO> buscarEmpresaPorCodigo(@PathVariable Long codigo) throws ParseException, EntityNotFoundException, IOException {
		return empresaService.findByIdApi(codigo);
	}

	@GetMapping("/v1/cnpj/{cnpj}")
	@Operation(description = "Buscar empresa pelo CNPJ cadastrado.")
	public ResponseEntity<EmpresaDTO> buscarEmpresaPorCnpj(@PathVariable Long cnpj) throws ParseException, IOException {
		return empresaService.empresaFindByCnpjApi(cnpj);
	}

	@GetMapping("/v1")
	@Operation(description = "Buscar lista de empresas cadastradas.")
	public ResponseEntity<List<EmpresaDTO>> buscarListaEmpresa() throws ParseException, IOException {
		return empresaService.empresaFindAll();
	}

	@PostMapping("/v1")
	@Operation(description = "Cadastrar uma empresa.")
	public ResponseEntity<EmpresaDTO> cadastrarEmpresa(@RequestBody EmpresaModel entity) throws ParseException, IOException {
		return empresaService.empresaSave(entity);
	}

	@PutMapping("/v1/{codigo}")
	@Operation(description = "Atualizar o cadastro de uma empresa passando o código cadastrado.")
	public ResponseEntity<EmpresaDTO> atualizarEmpresa(@PathVariable Long codigo, @RequestBody EmpresaModel entity) throws ParseException, IOException {
		return empresaService.empresaUpdate(codigo, entity);
	}

	@DeleteMapping("/v1/{codigo}")
	@Operation(description = "Deletar uma empresa pelo código cadastrado.")
	public ResponseEntity<EmpresaDTO> deletarEmpresa(@PathVariable Long codigo) throws ParseException, EntityNotFoundException, IOException {
		return empresaService.empresaDeleteById(codigo);
	}

}
