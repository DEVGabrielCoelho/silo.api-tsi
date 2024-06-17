package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.dto.FirmwareDTO;
import br.com.telematica.siloapi.model.entity.Firmware;
import br.com.telematica.siloapi.repository.FirmwareRepository;
import br.com.telematica.siloapi.utils.Utils;
import jakarta.persistence.EntityNotFoundException;

@Service
public class FirmwareServiceImpl {

	@Autowired
	private FirmwareRepository firmRepository;

	public FirmwareDTO update(Long codigo, MultipartFile file, String modelo) throws IOException {
		Objects.requireNonNull(codigo, "Código do Firmware está nulo.");
		Objects.requireNonNull(file, "Arquivo do Firmware está nulo.");
		Objects.requireNonNull(modelo, "Modelo está nulo");
		byte[] arq = file.getBytes();
		var firm = new Firmware(codigo, modelo, file.getContentType(), file.getOriginalFilename(), arq);
		return new FirmwareDTO(firmRepository.save(firm));
	}

	public FirmwareDTO save(MultipartFile file, String modelo) throws IOException {
		Objects.requireNonNull(file, "Arquivo do Firmware está nulo.");
		Objects.requireNonNull(modelo, "Número de série está nulo");
		byte[] arq = file.getBytes();
		var firm = new Firmware(null, modelo, file.getContentType(), file.getOriginalFilename(), arq);
		return new FirmwareDTO(firmRepository.save(firm));
	}

	public ResponseGlobalModel delete(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código está nulo");
		var firmId = firmRepository.findById(codigo);
		if (firmId.isEmpty())
			throw new EntityNotFoundException("Firmware não encontrado ou já está deletado.");
		firmRepository.deleteById(codigo);
		return Utils.responseMessageSucess("Firmware apagado com sucesso. Código do Firmware: " + codigo);
	}

	public ResponseEntity<Resource> findByIdDownload(Long codigo) throws NoSuchAlgorithmException {
		Objects.requireNonNull(codigo, "Código do Audio da Medição está nulo.");

		Firmware firm = firmRepository.findById(codigo).orElseThrow(() -> new EntityNotFoundException("Firmware não encontrado ou deletado."));

		byte[] arquivoBytes = firm.getFirarq();
		if (arquivoBytes == null)
			throw new EntityNotFoundException("Erro ao obter o Bytes do arquivo");

		byte[] hash = MessageDigest.getInstance("SHA-256").digest(arquivoBytes);
		String checksum = new BigInteger(1, hash).toString(16);
		Resource arquivoResource = new ByteArrayResource(arquivoBytes);

		String nomeArquivo = firm.getFirdesc().toString();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"");
		headers.add(HttpHeaders.CONTENT_TYPE, firm.getFirnam());
		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(arquivoBytes.length));
		headers.add("CheckSum", checksum);

		return ResponseEntity.ok().headers(headers).body(arquivoResource);
	}

	public Page<FirmwareDTO> findAllPaginado(Pageable pageable) {
		Objects.requireNonNull(pageable, "Pageable da Firmware está nulo.");
		Page<Firmware> result = firmRepository.findAll(pageable);

		return result.map(medicaoAudio -> new FirmwareDTO(medicaoAudio));
	}

	public List<FirmwareDTO> findByAll() {
		List<Firmware> result = firmRepository.findAll();

		return result.stream().map(firmware -> new FirmwareDTO(firmware)).collect(Collectors.toList());
	}

	public FirmwareDTO findById(Long codigo) {
		Objects.requireNonNull(codigo, "Pageable da Firmware está nulo.");
		Optional<Firmware> result = firmRepository.findById(codigo);
		if (!result.isPresent() || result.isEmpty()) {
			throw new EntityNotFoundException("Firmware não encontrada ou deletada.");
		}
		return result.map(medicaoAudio -> new FirmwareDTO(medicaoAudio)).get();

	}
}
