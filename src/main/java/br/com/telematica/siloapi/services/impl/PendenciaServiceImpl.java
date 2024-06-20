package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.PendenciaDelete;
import br.com.telematica.siloapi.model.PendenciaModel;
import br.com.telematica.siloapi.model.dto.KeepAliveDTO;
import br.com.telematica.siloapi.model.dto.PendenciasDTO;
import br.com.telematica.siloapi.model.entity.Firmware;
import br.com.telematica.siloapi.model.entity.Pendencia;
import br.com.telematica.siloapi.model.enums.StatusEnum;
import br.com.telematica.siloapi.repository.PendenciaRepository;
import br.com.telematica.siloapi.services.PendenciaServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PendenciaServiceImpl implements PendenciaServInterface {

	@Autowired
	private PendenciaRepository pendenciaRepository;

	@Override
	public ResponseEntity<KeepAliveDTO> findByKeepAlive(String num) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(num, "Número de Série está nulo.");

		// var modulo = sireneModuloService.findBySmonseEntity(num);
		// Date dateKeep = Utils.addGmtToDateTime(modulo.getSmogmt());
		// String dateKeepString = Utils.addGmtToDateTimeSendString(dateKeep);
		// sireneModuloService.registerKeepAliveInModulo(modulo, dateKeep);

		// var listaPendenciaNova =
		// findByPenStaAndpendel(StatusEnum.PENDENCIA.toString());
		Map<String, List<Map<String, Object>>> pendenciasAgrupadas = new HashMap<>();

		// for (PendenciasDTO pendencia : listaPendenciaNova) {
		// if (pendencia.getStatus() == StatusEnum.PENDENCIA && pendencia.getModulo() ==
		// modulo.getSmocod()) {
		// String tipoPendencia = pendencia.getTipoPendencia().toString();
		// long idPendencia = pendencia.getId();
		// Long codigoModulo = pendencia.getModulo();
		// Objects.requireNonNull(codigoModulo, "Código do módulo está nulo.");
		//
		// if (!pendenciasAgrupadas.containsKey(tipoPendencia)) {
		// pendenciasAgrupadas.put(tipoPendencia, new ArrayList<>());
		// }
		//
		// Map<String, Object> detalhesPendencia = new HashMap<>();
		// detalhesPendencia.put("id", idPendencia);
		// detalhesPendencia.put("numero_serie", modulo.getSmonse());
		// if (pendencia.getTipoPendencia() == PendenciaEnum.FIRMWARE)
		// detalhesPendencia.put("id_firmware", pendencia.getFirmware());
		//
		// pendenciasAgrupadas.get(tipoPendencia).add(detalhesPendencia);
		// }
		// }

		return MessageResponse.success(new KeepAliveDTO(null, pendenciasAgrupadas));
	}

	@Override
	public ResponseEntity<List<PendenciasDTO>> findByAll() {
		var pendList = pendenciaRepository.findByPendel(1);
		return MessageResponse
				.success(pendList.stream().map(pendDto -> new PendenciasDTO(pendDto)).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<List<PendenciasDTO>> findByPentipAndpendel(String tipo) {
		Objects.requireNonNull(tipo, "Tipo está nulo.");
		var pendList = pendenciaRepository.findByPentipAndPendel(tipo, 1);
		return MessageResponse
				.success(pendList.stream().map(pendDto -> new PendenciasDTO(pendDto)).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<List<PendenciasDTO>> findByPenStaAndpendel(String status) {
		Objects.requireNonNull(status, "Tipo está nulo.");
		var pendList = pendenciaRepository.findByPenstaAndPendel(status, 1);
		return MessageResponse
				.success(pendList.stream().map(pendDto -> new PendenciasDTO(pendDto)).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<PendenciasDTO> findById(Long id) {
		Objects.requireNonNull(id, "Id da Pendência está nulo.");
		var pendList = pendenciaRepository.findByPencodAndPendel(id, 1).get();
		return MessageResponse.success(new PendenciasDTO(pendList));
	}

	@Override
	public ResponseEntity<List<PendenciasDTO>> findByPentipAndPenstaAndpendel(String tipo, String status) {
		Objects.requireNonNull(tipo, "Tipo está nulo.");
		Objects.requireNonNull(status, "Status está nulo.");
		var pendList = pendenciaRepository.findByPentipAndPenstaAndPendel(tipo, status, 1);
		return MessageResponse
				.success(pendList.stream().map(pendDto -> new PendenciasDTO(pendDto)).collect(Collectors.toList()));
	}

	@Override
	public ResponseEntity<Page<PendenciasDTO>> findAllPaginado(String nome, Long modulo, Pageable pageable) {
		Objects.requireNonNull(pageable, "Pageable está nulo.");
		Specification<Pendencia> spec;
		spec = Pendencia.filterByFields(nome, modulo, 1);

		Page<Pendencia> result = pendenciaRepository.findAll(spec, pageable);

		return MessageResponse.success(result.map(medicaoAudio -> new PendenciasDTO(medicaoAudio)));

	}

	@Override
	public ResponseEntity<PendenciasDTO> delete(PendenciaDelete delete) throws ParseException, IOException {
		Long id = delete.getIdPendencia();
		Objects.requireNonNull(id, "Id da Pendência está nulo.");

		var pendList = pendenciaRepository.findByPencodAndPendel(id, 1)
				.orElseThrow(() -> new IOException("Pendência não encontrada com o ID: " + id));
		var pend = new Pendencia(id, pendList.getPentip(), delete.getStatus().toString(), delete.getDescricao(),
				pendList.getPenini(), new Date(), pendList.getSmocod(), null, 0);

		pendenciaRepository.save(pend);
		return MessageResponse.success(new PendenciasDTO());
	}

	@Override
	public ResponseEntity<PendenciasDTO> update(Long id, StatusEnum pendenciaModulo) throws ParseException {
		Objects.requireNonNull(id, "Id da Pendência está nulo.");
		Objects.requireNonNull(pendenciaModulo, "Status da pendência está nulo.");
		var pendList = pendenciaRepository.findByPencodAndPendel(id, 1).get();
		Firmware pendCheck = null;
		if (pendenciaModulo == StatusEnum.FINALIZADO || pendenciaModulo == StatusEnum.PENDENCIA)
			pendCheck = null;
		var pend = new Pendencia(id, pendList.getPentip(), pendenciaModulo.toString(), pendList.getPendes(),
				pendList.getPenini(), pendenciaModulo == StatusEnum.FINALIZADO ? new Date() : null, pendList.getSmocod(),
				pendCheck, pendList.getPendel());
		return MessageResponse.success(new PendenciasDTO(pendenciaRepository.save(pend)));

	}

	@Override
	public ResponseEntity<PendenciasDTO> save(PendenciaModel pendenciaModulo)
			throws EntityNotFoundException, IOException {
		String numSerie = pendenciaModulo.getNumSerie();
		Objects.requireNonNull(pendenciaModulo.getStatus(), "Status da pendencia está nulo.");
		Objects.requireNonNull(pendenciaModulo.getTipoPendencia(), "Tipo da Pendência está nulo.");
		Objects.requireNonNull(pendenciaModulo.getDescricao(), "Descrição da Pendência está nulo.");
		Objects.requireNonNull(numSerie, "Número de Série está nulo.");
		// var modulo = sireneModuloService.findByNumSerie(numSerie);
		var pend = new Pendencia(null, pendenciaModulo.getTipoPendencia().toString(),
				pendenciaModulo.getStatus().toString(), pendenciaModulo.getDescricao(), new Date(), null, null, null, 1);
		return MessageResponse.success(new PendenciasDTO(pendenciaRepository.save(pend)));
	}

}
