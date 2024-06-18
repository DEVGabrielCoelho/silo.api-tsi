package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.MedicaoModel;
import br.com.telematica.siloapi.model.dto.MedicaoDTO;
import br.com.telematica.siloapi.services.MedicaoServInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/medicao")
@Tag(name = "Medições", description = "Medições API")
public class MedicaoController extends SecurityRestController {

	@Autowired
	private MedicaoServInterface medicaoService;

	@GetMapping("/v1/buscarMedicoes")
	@Operation(description = "Busca pelas medição registrada")
	public ResponseEntity<List<MedicaoDTO>> getMedicao() throws IOException {
		return medicaoService.findAllMedicaoDTO();
	}

	@PostMapping("/v1/cadastraMedicao")
	@Operation(description = "Cadastro de uma nova medição")
	public ResponseEntity<MedicaoDTO> createMedicao(@RequestBody MedicaoModel medicaoDTO) throws IOException {
		return medicaoService.save(medicaoDTO);
	}

	@PutMapping("/v1/atualizaMedicao")
	@Operation(description = "Atualização de uma medição")
	public ResponseEntity<MedicaoDTO> updateMedicao(@RequestBody MedicaoModel medicaoDTO) throws IOException {
		return medicaoService.update(medicaoDTO);
	}

	@DeleteMapping("/v1/deletarMedicao")
	@Operation(description = "Deletar uma medição, formato de data desejado 'yyyy-MM-dd HH:mm:ss'")
	public ResponseEntity<MedicaoDTO> deleteMedicao(@RequestParam(name = "dataMedicao", required = true) String dataMedicao) throws Exception {
		return medicaoService.deleteByMsidth(dataMedicao);

	}

}
