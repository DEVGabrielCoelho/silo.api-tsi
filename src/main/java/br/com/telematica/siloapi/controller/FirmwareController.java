package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.dto.FirmwareDTO;
import br.com.telematica.siloapi.services.impl.FirmwareServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("api/pendencia-firmware")
@Tag(name = "Pendencias", description = "API para controle de pendencias")
public class FirmwareController extends SecurityRestController {

	@Autowired
	private FirmwareServiceImpl firmService;

	@GetMapping("/v1/paginado")
	@Operation(description = "Busca paginada de firmware. Pagina define em qual pagina deseja carregar, Tamanho define a quantidade de itens que deseja trazer por pagina.")
	public ResponseEntity<Page<FirmwareDTO>> buscarFirmwarePaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho) {
		return ResponseEntity.ok(firmService.findAllPaginado(PageRequest.of(pagina, tamanho)));
	}

	@GetMapping("/v1")
	@Operation(description = "Buscar lista de Firmware cadastrados.")
	public ResponseEntity<List<FirmwareDTO>> BuscarListaFirmware() throws ParseException {
		return ResponseEntity.ok(firmService.findByAll());
	}

	@GetMapping("/v1/{codigo}")
	@Operation(description = "Buscar pelo código o Firmware cadastrado.")
	public ResponseEntity<FirmwareDTO> buscarFirmwarePorId(@PathVariable Long codigo) throws ParseException {
		return ResponseEntity.ok(firmService.findById(codigo));
	}

	@GetMapping("/v1/download/{codigo}")
	@Operation(description = "Downloado pelo código do Firmware cadastrado.")
	public ResponseEntity<Resource> buscarFirmwareParaDownload(@PathVariable Long codigo) throws ParseException, NoSuchAlgorithmException {
		return firmService.findByIdDownload(codigo);
	}

	@PostMapping(path = "/v1", consumes = { "multipart/form-data" })
	@Operation(description = "Cadastrar um firmware. Necessário passar o arquivo do firmware e o Modelo correspondente para identificação.")
	public ResponseEntity<FirmwareDTO> cadastrarFirmware(@RequestParam("arquivo") MultipartFile arquivo, @RequestParam(name = "modelo") String modelo) throws IOException {
		var save = firmService.save(arquivo, modelo);
		return ResponseEntity.ok(save);
	}

	@PutMapping(path = "/v1", consumes = { "multipart/form-data" })
	@Operation(description = "Atualizar um firmware. Necessário o Código do firmware cadastrado, passar o arquivo do firmware e o Modelo correspondente para identificação caso deseje alterar.")
	public ResponseEntity<FirmwareDTO> atualizarFirmware(@RequestParam("codigo") Long codigo, @RequestParam("arquivo") MultipartFile arquivo, @RequestParam("numSerie") String numSerie) throws IOException {
		var save = firmService.update(codigo, arquivo, numSerie);
		return ResponseEntity.ok(save);
	}

	@DeleteMapping("/v1/{codigo}")
	@Operation(description = "Deletar um firmware. Necessário o Código do firmware cadastrado.")
	public ResponseEntity<ResponseGlobalModel> deletarFirmware(@PathVariable Long codigo) throws IOException {
		return ResponseEntity.ok(firmService.delete(codigo));
	}
}
