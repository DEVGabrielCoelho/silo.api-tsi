package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.LoggerModel;
import br.com.telematica.siloapi.model.dto.LoggerDTO;
import br.com.telematica.siloapi.services.impl.LoggerServiceImpl;
import br.com.telematica.siloapi.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@CrossOrigin
@RestController
@RequestMapping("api/logger")
@Tag(name = "Logger", description = "API para controle de logs")
public class LoggerController extends SecurityRestController {

	@Autowired
	private LoggerServiceImpl logServ;

	@GetMapping("/v1/paginado")
	@Operation(description = "Busca paginada de logger. Pagina define a pagina que deseja acessar, Tamanho define a quantidade de itens por pagina, Direção definie a ordenação ASC - Ascendente / DESC - Descendente, Filtro busca pelo data, tipoLoggger e mensagem, DataInicio e DataFim filtra pelo intervalo da data (caso seja o filtro seja só por dataInicio/Fim, filtro busca do dataInicio -> Hoje e dataFim -> até data do log mais antigo), OrdernarPor requer os sequintes campos: data, numSerie, tipoLogger. ")
	public ResponseEntity<Page<LoggerDTO>> buscarLoggerPaginado(@RequestParam(value = "pagina", defaultValue = "0")@NonNull  Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10")@NonNull  Integer tamanho, @RequestParam(value = "direcao", defaultValue = "ASC") @NonNull String direcao,
			@RequestParam(value = "ordenarPor", defaultValue = "data") String ordenarPor,
			// @RequestParam(value = "modulo", required = false) Long modulo,
			@RequestParam(value = "filtro", required = false) String filtro, @RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate) throws EntityNotFoundException, IOException {

		String ordenarEntity = LoggerDTO.consultaPagable(ordenarPor);
		if (ordenarEntity == null) {
			return ResponseEntity.badRequest().body(Page.empty());
		}

		Page<LoggerDTO> result = logServ.findByAllPaginado(null, filtro, startDate, endDate, Utils.consultaPage(ordenarEntity, direcao, pagina, tamanho));
		return ResponseEntity.ok(result);
	}

	@GetMapping("/v1")
	public ResponseEntity<List<LoggerDTO>> buscarListaLogger() throws EntityNotFoundException, IOException {
		return ResponseEntity.ok(logServ.findByAll());
	}

	@PostMapping("/v1")
	public ResponseEntity<LoggerDTO> cadastrarLogger(@RequestBody LoggerModel entity) throws EntityNotFoundException, IOException {
		return ResponseEntity.ok(logServ.save(entity));
	}

}
