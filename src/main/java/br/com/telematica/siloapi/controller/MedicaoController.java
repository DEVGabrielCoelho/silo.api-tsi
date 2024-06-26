package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.MedicaoDeviceModel;
import br.com.telematica.siloapi.model.MedicaoModel;
import br.com.telematica.siloapi.model.dto.MedicaoDTO;
import br.com.telematica.siloapi.services.MedicaoServInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/medicao")
@Tag(name = "Medições", description = "API para gerenciamento de medições")
public class MedicaoController extends SecurityRestController {

	@Autowired
	private MedicaoServInterface medicaoService;

	@GetMapping("/v1/listar")
	@Operation(description = "Buscar todas as medições registradas. Retorna uma lista de todas as medições existentes.")
	public ResponseEntity<List<MedicaoDTO>> getMedicao() throws IOException {
		return medicaoService.findAllMedicaoDTO();
	}

	@PostMapping("/v1/criar")
	@Operation(description = "Criar uma nova medição. Recebe os detalhes da medição e a armazena no sistema.")
	public ResponseEntity<MedicaoDTO> createMedicao(@RequestBody MedicaoModel medicaoDTO) throws IOException, ParseException {
		return medicaoService.save(medicaoDTO);
	}

	@PostMapping("/v1/criarMedicao")
	@Operation(description = "Criar uma nova medição. Recebe os detalhes da medição e a armazena no sistema.")
	public ResponseEntity<MedicaoDTO> createMedicao(@RequestBody MedicaoDeviceModel medicaoDTO) throws IOException, ParseException {
		return medicaoService.saveData(medicaoDTO);
	}

	@PutMapping("/v1/atualiza")
	@Operation(description = "Atualizar uma medição existente. Atualiza os detalhes de uma medição com base nas informações fornecidas.")
	public ResponseEntity<MedicaoDTO> updateMedicao(@RequestBody MedicaoModel medicaoDTO) throws IOException, ParseException {
		return medicaoService.update(medicaoDTO);
	}

	@DeleteMapping("/v1/deletar")
	@Operation(description = "Deletar uma medição pelo ID. Remove uma medição específica com base no ID fornecido.")
	public ResponseEntity<MedicaoDTO> deleteMedicao(@RequestParam(name = "dataMedicao", required = true) String dataMedicao) throws IOException, ParseException {
		return medicaoService.deleteByMsidth(dataMedicao);
	}

	@GetMapping("/v1/paginado")
	public ResponseEntity<Page<MedicaoDTO>> findAllPaginado(@RequestParam(value = "filtro", required = false) String filtro, @RequestParam(value = "dataInicio", required = false) String dataInicio, @RequestParam(value = "dataFim", required = false) String dataFim,
			@RequestParam(value = "pagina", defaultValue = "0") int pagina, @RequestParam(value = "tamanho", defaultValue = "10") int tamanho, @RequestParam(value = "ordenarPor", defaultValue = "msidth") String ordenarPor, @RequestParam(value = "direcao", defaultValue = "ASC") String direcao) {

		Sort sort = Sort.by(Sort.Direction.fromString(direcao), ordenarPor);
		Pageable pageable = PageRequest.of(pagina, tamanho, sort);

		return medicaoService.medicaoFindAllPaginado(filtro, dataInicio, dataFim, pageable);
	}
}
