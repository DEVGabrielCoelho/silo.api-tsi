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

import br.com.telematica.siloapi.model.dto.FirmwareDTO;
import br.com.telematica.siloapi.services.FirmwareServInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("api/pendencia-firmware")
@Tag(name = "Pendências", description = "API para controle e gerenciamento de firmware")
public class FirmwareController extends SecurityRestController {

	@Autowired
	private FirmwareServInterface firmService;

	@GetMapping("/v1/paginado")
	@Operation(description = "Busca paginada de firmware. Retorna uma lista paginada de firmware com opções de filtragem.")
	public ResponseEntity<Page<FirmwareDTO>> buscarFirmwarePaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho) {
		return firmService.findAllPaginado(PageRequest.of(pagina, tamanho));
	}

	@GetMapping("/v1/listar")
	@Operation(description = "Listar todos os firmwares cadastrados. Retorna uma lista de todos os firmwares existentes.")
	public ResponseEntity<List<FirmwareDTO>> BuscarListaFirmware() throws ParseException {
		return firmService.findAll();
	}

	@GetMapping("/v1/buscar/{codigo}")
	@Operation(description = "Buscar firmware pelo código. Retorna os detalhes de um firmware específico com base no código fornecido.")
	public ResponseEntity<FirmwareDTO> buscarFirmwarePorId(@PathVariable Long codigo) throws ParseException {
		return firmService.findById(codigo);
	}

	@GetMapping("/v1/download/{codigo}")
	@Operation(description = "Download do firmware pelo código. Permite o download de um firmware específico com base no código fornecido.")
	public ResponseEntity<Resource> buscarFirmwareParaDownload(@PathVariable Long codigo) throws ParseException, NoSuchAlgorithmException {
		return firmService.findByIdDownload(codigo);
	}

	@PostMapping(path = "/v1/criar", consumes = { "multipart/form-data" })
	@Operation(description = "Criar um novo firmware. Recebe o arquivo e os detalhes do firmware e o armazena no sistema.")
	public ResponseEntity<FirmwareDTO> criarFirmware(@RequestParam("arquivo") MultipartFile arquivo, @RequestParam(name = "modelo") String modelo) throws IOException {
		var save = firmService.save(arquivo, modelo);
		return save;
	}

	@PutMapping(path = "/v1/atualizar", consumes = { "multipart/form-data" })
	@Operation(description = "Atualizar um firmware existente. Recebe o código, o novo arquivo e os detalhes do firmware e os atualiza no sistema.")
	public ResponseEntity<FirmwareDTO> atualizarFirmware(@RequestParam("codigo") Long codigo, @RequestParam("arquivo") MultipartFile arquivo, @RequestParam("numSerie") String numSerie) throws IOException {
		var save = firmService.update(codigo, arquivo, numSerie);
		return save;
	}

	@DeleteMapping("/v1/deletar/{codigo}")
	@Operation(description = "Deletar um firmware pelo código. Remove um firmware específico com base no código fornecido.")
	public ResponseEntity<FirmwareDTO> deletarFirmware(@PathVariable Long codigo) throws IOException {
		return firmService.delete(codigo);
	}
}
