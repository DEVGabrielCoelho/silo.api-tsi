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

import br.com.telematica.siloapi.model.dto.MedicaoDTO;
import br.com.telematica.siloapi.services.MedicaoInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicao")
@Tag(name = "Medições", description = "Medições API")
public class MedicaoController extends SecurityRestController {

	@Autowired
	private MedicaoInterface medicaoService;

	@GetMapping("/v1/buscarMedicoes")
	@Operation(description = "Busca pelas medição registrada")
	public ResponseEntity<Object> getMedicao() throws IOException {
		return medicaoService.findAllMedicaoDTO();
	}

	@PostMapping("/v1/cadastraMedicao")
	@Operation(description = "Cadastro de uma nova medição")
	public ResponseEntity<Object> createMedicao(@Valid @RequestBody MedicaoDTO medicaoDTO) {
		return medicaoService.save(medicaoDTO);
	}

	@PutMapping("/v1/atualizaMedicao")
	@Operation(description = "Atualização de uma medição")
	public ResponseEntity<Object> updateMedicao(@Valid @RequestBody MedicaoDTO medicaoDTO) throws IOException, Exception {
		return medicaoService.update(medicaoDTO);
	}

	@DeleteMapping("/v1/deletarMedicao/{dataMedicao}")
	@Operation(description = "Deletar uma medição")
	public ResponseEntity<Object> deleteMedicao(@Valid @PathVariable("dataMedicao") String dataMedicao) throws Exception {
		return medicaoService.deleteByMsidth(dataMedicao);

	}

}
