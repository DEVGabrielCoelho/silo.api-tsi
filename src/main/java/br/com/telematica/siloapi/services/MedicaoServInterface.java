package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.MedicaoDeviceModel;
import br.com.telematica.siloapi.model.MedicaoModel;
import br.com.telematica.siloapi.model.dto.MedicaoDTO;
import br.com.telematica.siloapi.model.entity.Medicao;

public interface MedicaoServInterface {

	ResponseEntity<MedicaoDTO> deleteByMsidth(String msidth) throws IOException, ParseException;

	List<Medicao> findAll() throws IOException;

	ResponseEntity<List<MedicaoDTO>> findAllMedicaoDTO() throws IOException;

	MedicaoDTO findByData(Date date) throws IOException;

	ResponseEntity<MedicaoDTO> save(MedicaoModel medicaoDTO) throws IOException, ParseException;

	ResponseEntity<MedicaoDTO> saveData(MedicaoDeviceModel medicaoDTO) throws IOException, ParseException;

	ResponseEntity<MedicaoDTO> update(MedicaoModel medicaoDTO) throws IOException, ParseException;

	ResponseEntity<Page<MedicaoDTO>> medicaoFindAllPaginado(String searchTerm, String dataInicio, String dataFim, Pageable pageable);
}
