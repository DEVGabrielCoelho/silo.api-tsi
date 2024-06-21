package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.CustomMessageException;
import br.com.telematica.siloapi.model.PlantaModel;
import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.entity.Planta;
import br.com.telematica.siloapi.repository.PlantaRepository;
import br.com.telematica.siloapi.services.PlantaServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PlantaServiceImpl implements PlantaServInterface {

    private static final Logger logger = LoggerFactory.getLogger(PlantaServiceImpl.class);
    private static final String RECURSO = "Planta";

    @Autowired
    private PlantaRepository plantaRepository;

    @Autowired
    private EmpresaServiceImpl empresaServiceImpl;

    @Override
    public ResponseEntity<PlantaDTO> save(PlantaModel planta) throws IOException {
        validatePlantaFields(planta);

        try {
            var emp = empresaServiceImpl.findById(planta.getEmpresa());
            var entity = new Planta();
            entity.plantaUpdateOrSave(planta.getNome(), emp);

            Planta savedPlanta = plantaRepository.save(entity);
            logger.info("Planta salva com sucesso: " + savedPlanta);

            return MessageResponse.success(new PlantaDTO(savedPlanta));
        } catch (IOException e) {
            logger.error("Erro ao salvar a planta: ", e);
            throw CustomMessageException.exceptionIOException("salvar", RECURSO, planta, e);
        }
    }

    @Override
    public ResponseEntity<PlantaDTO> deleteByPlacod(Long codigo) throws IOException {
        try {
            var entity = findEntity(codigo);
            if (entity == null) {
                throw new EntityNotFoundException("Não foi possível encontrar a Planta com o ID fornecido.");
            }

            plantaRepository.removeByPlacod(entity.getPlacod());
            return MessageResponse.success(null);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Não foi possível encontrar a Planta com o ID fornecido. Error: ", e);
            throw CustomMessageException.exceptionEntityNotFoundException(codigo, RECURSO, e);
        } 
    }

    @Override
    public ResponseEntity<PlantaDTO> update(Long codigo, PlantaModel planta) throws IOException {
        Objects.requireNonNull(codigo, "Código da Planta está nulo.");
        validatePlantaFields(planta);

        try {
            var entity = findEntity(codigo);
            var emp = empresaServiceImpl.findById(planta.getEmpresa());
            entity.plantaUpdateOrSave(planta.getNome(), emp);

            Planta updatedPlanta = plantaRepository.save(entity);
            logger.info("Planta atualizada com sucesso: " + updatedPlanta);

            return MessageResponse.success(new PlantaDTO(updatedPlanta));
        } catch (IOException e) {
            logger.error("Erro ao atualizar a planta: ", e);
            throw CustomMessageException.exceptionCodigoIOException("atualizar", RECURSO, codigo, planta, e);
        }
    }

    @Override
    public List<Planta> findAll() throws IOException {
        return plantaRepository.findAll();
    }

    @Override
    public ResponseEntity<List<PlantaDTO>> findAllPlantaDTO() throws IOException {
        List<PlantaDTO> plantaDTOs = plantaRepository.findAll().stream()
                .map(this::convertToPlantaDTO)
                .collect(Collectors.toList());
        return MessageResponse.success(plantaDTOs);
    }

    @Override
    public ResponseEntity<PlantaDTO> findById(Long codigo) throws IOException, EmptyResultDataAccessException {
        Objects.requireNonNull(codigo, "Código da Planta está nulo.");

        Planta result = findEntity(codigo);
        if (result == null) {
            logger.error("Planta não encontrada.");
            throw new EntityNotFoundException("Planta não encontrada com o código: " + codigo);
        }

        return MessageResponse.success(new PlantaDTO(result));
    }

    private PlantaDTO convertToPlantaDTO(Planta plantaEntity) {
        return new PlantaDTO(plantaEntity);
    }

    Planta findEntity(Long codigo) {
        Objects.requireNonNull(codigo, "Código está nulo.");
        return plantaRepository.findById(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Planta não encontrada com o código: " + codigo));
    }

    private void validatePlantaFields(PlantaModel planta) {
        Objects.requireNonNull(planta.getEmpresa(), "Código da Empresa está nulo.");
        Objects.requireNonNull(planta.getNome(), "Nome da planta está nulo.");
    }
}
