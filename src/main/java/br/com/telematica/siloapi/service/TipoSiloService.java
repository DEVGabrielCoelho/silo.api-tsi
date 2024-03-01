package br.com.telematica.siloapi.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import br.com.telematica.siloapi.model.entity.TipoSiloEntity;
import br.com.telematica.siloapi.repository.TipoSiloRepository;
import ch.qos.logback.classic.Logger;
import jakarta.transaction.Transactional;

@Service
public class TipoSiloService {

    private static Logger logger = (Logger) LoggerFactory.getLogger(TipoSiloService.class);
    @Autowired
    private TipoSiloRepository tipoSiloRepository;

    public TipoSiloDTO save(TipoSiloDTO tipoSiloDTO) throws RuntimeException {
        try {

            if (tipoSiloDTO == null) {
                throw new RuntimeException("Planta está nula.");
            }
            // Integer silcod, Integer tsicod, String silnom, Integer placod
            var entity = new TipoSiloEntity(tipoSiloDTO.getCodigo(), tipoSiloDTO.getEmpresa(), tipoSiloDTO.getDescricao());
            var result = tipoSiloRepository.save(entity);

            logger.info("Planta salva com sucesso." + result);
            return new TipoSiloDTO(result.getTsicod(), result.getEmpcod(), result.getTsides());
        } catch (Exception e) {
            throw new RuntimeException("Exceção:" + e.getCause());
        }
    }

    @Transactional
    public void deleteByTsicod(Integer codigo) throws IOException {
        if (codigo == null) {
            throw new IOException("O ID do silo está nulo.");
        }
        try {
            tipoSiloRepository.deleteByTsicod(codigo);

        } catch (EmptyResultDataAccessException e) {
            logger.error("Não foi possível encontrar o silo com o ID fornecido. Error: " + e.getCause());
            throw new IOException("Não foi possível encontrar o silo com o ID fornecido.", e);
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao excluir o silo. Error: " + e.getCause());
            throw new IOException("Ocorreu um erro ao excluir o silo.", e.getCause());
        }
    }

    public TipoSiloDTO update(TipoSiloDTO tipoSiloDTO) throws IOException {
        if (tipoSiloDTO == null) {
            throw new RuntimeException("Planta está nulo.");
        }
        var entity = new TipoSiloEntity(tipoSiloDTO.getCodigo(), tipoSiloDTO.getEmpresa(), tipoSiloDTO.getDescricao());
        var result = tipoSiloRepository.save(entity);
        logger.info("Planta atualizado com sucesso." + result);
        return new TipoSiloDTO(result.getTsicod(), result.getEmpcod(), result.getTsides());
    }

    public List<TipoSiloEntity> findAll() throws IOException {
        return tipoSiloRepository.findAll();
    }

    public List<TipoSiloDTO> findAllTipoSiloDTO() throws IOException {
        return tipoSiloRepository.findAll().stream().map(this::convertToTipoSiloDTO).collect(Collectors.toList());
    }

    public TipoSiloDTO findById(Integer id) throws IOException, EmptyResultDataAccessException {
        if (id == null) {
            throw new IOException("Id está nulo.");
        }
        var result = tipoSiloRepository.findById(id).orElse(null);

        if (result == null) {
            throw new EmptyResultDataAccessException("Planta não encontrada.", 1);
        }
        return new TipoSiloDTO(result.getTsicod(), result.getEmpcod(), result.getTsides());
    }

    private TipoSiloDTO convertToTipoSiloDTO(TipoSiloEntity siloDTOEntity) {
        return new TipoSiloDTO(siloDTOEntity);
    }

}
