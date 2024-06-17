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

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.PendenciaDelete;
import br.com.telematica.siloapi.model.PendenciaModel;
import br.com.telematica.siloapi.model.dto.KeepAliveDTO;
import br.com.telematica.siloapi.model.dto.PendenciasDTO;
import br.com.telematica.siloapi.model.enums.StatusEnum;
import br.com.telematica.siloapi.services.impl.PendenciaServiceImpl;
import br.com.telematica.siloapi.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("api/pendencia")
@Tag(name = "Pendencias", description = "API para controle de pendencias")
public class PendencyController extends SecurityRestController {

	@Autowired
	private PendenciaServiceImpl pendenciaService;

	@GetMapping("/v1/keepAlive/{numero_de_serie}")
	@Operation(description = "KeepAlive de módulo para atualização de DataHora e consulta de pendencia por módulo.")
	public ResponseEntity<KeepAliveDTO> buscarPendenciaDeModuloPorNumSerie(@Valid @PathVariable @NonNull String numero_de_serie) throws EntityNotFoundException, IOException {
		return ResponseEntity.ok(pendenciaService.findByKeepAlive(numero_de_serie));
	}

	@GetMapping("/v1/paginado")
	@Operation(description = "Busca de pendencias paginadas. Pagina define qual a pagina deseja ver, Tamanho define a quantidade de itens por pagina, Filtro permite filtrar os seguintes campos Código, TipoPendencia,  Status, DataInicio e DataFim, Direção definie a ordenação ASC - Ascendente / DESC - Descendente ,OrdenarPor requer os seguintes dados: id, tipoPendencia, status, dataInicio, penfim, modulo.")
	public ResponseEntity<Page<PendenciasDTO>> buscarPendenciaPaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho, @RequestParam(value = "filtro", required = false) String filtro,
			@RequestParam(value = "modulo", required = false) Long modulo, @RequestParam(value = "ordenarPor", defaultValue = "id") String ordenarPor, @RequestParam(value = "direcao", defaultValue = "ASC", required = false) String direcao) throws EntityNotFoundException, IOException {
		String ordenarEntity = PendenciasDTO.consultaPagable(ordenarPor);
		if (ordenarEntity == null) {
			return ResponseEntity.badRequest().body(Page.empty());
		}
		return ResponseEntity.ok(pendenciaService.findAllPaginado(filtro, modulo, Utils.consultaPage(ordenarEntity, direcao, pagina, tamanho)));
	}

	@GetMapping("/v1")
	@Operation(description = "Buscar lista de pendencias cadastradas.")
	public ResponseEntity<List<PendenciasDTO>> buscarListaPendencia() throws ParseException {
		return ResponseEntity.ok(pendenciaService.findByAll());
	}

	@PostMapping("/v1")
	@Operation(description = "Cadastrar uma pendencia.")
	public ResponseEntity<PendenciasDTO> cadastrarPendencia(@Valid @RequestBody PendenciaModel entity) throws EntityNotFoundException, IOException {
		return ResponseEntity.ok(pendenciaService.save(entity));
	}

	@PutMapping("/v1")
	@Operation(description = "Atualizar uma pendencia pelo id da pendencia e o status.")
	public ResponseEntity<PendenciasDTO> atualizarPendencia(@Valid @RequestParam("idPendencia") Long idPendencia, @Valid @RequestParam("status") StatusEnum status) throws ParseException {
		return ResponseEntity.ok(pendenciaService.update(idPendencia, status));
	}

	@DeleteMapping("/v1")
	@Operation(description = "Deletar uma pendencia.")
	public ResponseEntity<ResponseGlobalModel> deletarPendencia(@RequestBody PendenciaDelete delete) throws ParseException, IOException {
		return ResponseEntity.ok(pendenciaService.delete(delete));
	}
}
