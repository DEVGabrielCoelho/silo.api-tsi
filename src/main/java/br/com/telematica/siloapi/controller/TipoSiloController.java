package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.TipoSiloModel;
import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import br.com.telematica.siloapi.services.TipoSiloServInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tipo-silo")
@Tag(name = "Tipo Silo", description = "API para gerenciamento de tipos de silos")
public class TipoSiloController extends SecurityRestController {

	@Autowired
	private TipoSiloServInterface tipoSiloInterface;

	@GetMapping("/v1/listar")
	@Operation(description = "Busca pelos tipos de silos cadastrados. Retorna uma lista de todos os tipos de silos existentes.")
	public ResponseEntity<List<TipoSiloDTO>> getSilo() throws IOException {
		return tipoSiloInterface.findAllTipoSiloDTO();
	}

	@PostMapping("/v1/criar")
	@Operation(description = "Cadastro de um novo tipo de silo. Recebe os detalhes do tipo de silo e o armazena no sistema.")
	public ResponseEntity<TipoSiloDTO> createSilo(@Valid @RequestBody TipoSiloModel tipoSilo) throws IOException {
		return tipoSiloInterface.save(tipoSilo);
	}

	@PutMapping("/v1/atualizar/{codigo}")
	@Operation(description = "Atualização de um tipo de silo existente. Atualiza os detalhes de um tipo de silo com base no código fornecido.")
	public ResponseEntity<TipoSiloDTO> updateSilo(@Valid @PathVariable("codigo") Long codigo, @Valid @RequestBody TipoSiloModel tipoSilo) throws IOException {
		return tipoSiloInterface.update(codigo, tipoSilo);
	}

	@DeleteMapping("/v1/deletar/{codigo}")
	@Operation(description = "Deletar um tipo de silo pelo código. Remove um tipo de silo específico com base no código fornecido.")
	public ResponseEntity<TipoSiloDTO> deleteSilo(@Valid @PathVariable("codigo") Long codigo) throws IOException {
		return tipoSiloInterface.deleteByTsicod(codigo);
	}

	@GetMapping("/v1/paginado")
	@Operation(description = "Busca uma lista paginada de tipo de silo com base em critérios de busca e ordenação. Este endpoint permite a busca por um termo específico, além de suportar paginação e ordenação dos resultados.")
	public ResponseEntity<Page<TipoSiloDTO>> findAllPaginado(@RequestParam(value = "filtro", required = false) String filtro, @RequestParam(value = "pagina", defaultValue = "0") int pagina, @RequestParam(value = "tamanho", defaultValue = "10") int tamanho,
			@RequestParam(value = "ordenarPor", defaultValue = "codigo") String ordenarPor, @RequestParam(value = "direcao", defaultValue = "ASC") String direcao) throws EntityNotFoundException, IOException {

		Sort sort = Sort.by(Sort.Direction.fromString(direcao), filtrarDirecao(ordenarPor));
		Pageable pageable = PageRequest.of(pagina, tamanho, sort);

		return tipoSiloInterface.tipoSiloFindAllPaginado(filtro, pageable);
	}

	public String filtrarDirecao(String str) {
		switch (str) {
		case "codigo" -> {
			return "tsicod";
		}
		case "nome" -> {
			return "tsinom";
		}
		case "descricao" -> {
			return "tsides";
		}
		case "tipoSilo" -> {
			return "tsitip";
		}
		default -> throw new AssertionError();
		}
	}

}
