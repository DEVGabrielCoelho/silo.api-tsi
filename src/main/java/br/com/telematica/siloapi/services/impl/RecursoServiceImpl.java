package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.RecursoModel;
import br.com.telematica.siloapi.model.dto.RecursoDTO;
import br.com.telematica.siloapi.model.entity.Recurso;
import br.com.telematica.siloapi.model.enums.RecursoMapEnum;
import br.com.telematica.siloapi.repository.RecursoRepository;
import br.com.telematica.siloapi.services.RecursoServInterface;
import br.com.telematica.siloapi.utils.Utils;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RecursoServiceImpl implements RecursoServInterface {

    @Autowired
    private RecursoRepository recursoRepository;

    public Recurso findByIdEntity(@NonNull Long codigo) {
        return recursoRepository.findById(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Recurso não encontrado com o código: " + codigo));
    }

    public Recurso findByIdEntity(@NonNull String nome) {
        return recursoRepository.findByRecnom(nome)
                .orElseThrow(() -> new EntityNotFoundException("Recurso não encontrado com o nome: " + nome));
    }

    public RecursoDTO findByIdApi(@NonNull String nome) throws EntityNotFoundException, IOException {
        Recurso recurso = findByIdEntity(nome);
        return new RecursoDTO(recurso);
    }

    public RecursoDTO findByIdApi(@NonNull Long codigo) throws EntityNotFoundException, IOException {
        Recurso recurso = findByIdEntity(codigo);
        return new RecursoDTO(recurso);
    }

    public Recurso saveEntity(RecursoModel recursoModel) {
        validateRecursoModelFields(recursoModel);
        Recurso recurso = new Recurso(
                null,
                RecursoMapEnum.mapDescricaoToNome(recursoModel.getNome().getNome()),
                recursoModel.getDescricao()
        );
        return recursoRepository.save(recurso);
    }

    public Recurso updateEntity(Long codigo, RecursoModel recursoModel) {
        Objects.requireNonNull(codigo, "Código do Recurso está nulo.");
        validateRecursoModelFields(recursoModel);
        Recurso recurso = new Recurso(
                codigo,
                recursoModel.getNome().getNome(),
                recursoModel.getDescricao()
        );
        return recursoRepository.save(recurso);
    }

    public List<Recurso> findAllEntity() throws EntityNotFoundException, IOException {
        return recursoRepository.findAll();
    }

    public ResponseGlobalModel deleteEntity(@NonNull Long codigo) throws IOException {
        Objects.requireNonNull(codigo, "Código do Recurso está nulo.");
        try {
            recursoRepository.deleteById(codigo);
            return Utils.responseMessageSucess("Apagado com sucesso.");
        } catch (Exception e) {
            throw new IOException("Erro ao apagar o item. Mensagem: " + e.getMessage(), e);
        }
    }

    public Page<Recurso> findAllEntity(String nome, Pageable pageable) throws EntityNotFoundException, IOException {
        Objects.requireNonNull(pageable, "Pageable do Recurso está nulo.");
        if (nome == null) {
            return recursoRepository.findAll(pageable);
        } else {
            return recursoRepository.findByRecnomLike(nome, pageable);
        }
    }

    @Override
    public ResponseEntity<Page<RecursoDTO>> findAll(String nome, Pageable pageable) throws EntityNotFoundException, IOException {
        Page<Recurso> recursos = findAllEntity(nome, pageable);
        return MessageResponse.success(recursos.map(RecursoDTO::new));
    }

    @Override
    public ResponseEntity<RecursoDTO> save(RecursoModel recursoModel) {
        Recurso recurso = saveEntity(recursoModel);
        return MessageResponse.success(new RecursoDTO(recurso));
    }

    @Override
    public ResponseEntity<RecursoDTO> update(Long codigo, RecursoModel recursoModel) {
        Recurso recurso = updateEntity(codigo, recursoModel);
        return MessageResponse.success(new RecursoDTO(recurso));
    }

    @Override
    public ResponseEntity<List<RecursoDTO>> findAll() throws EntityNotFoundException, IOException {
        List<RecursoDTO> recursos = findAllEntity().stream()
                .map(RecursoDTO::new)
                .collect(Collectors.toList());
        return MessageResponse.success(recursos);
    }

    @Override
    public ResponseEntity<RecursoDTO> findById(@NonNull Long codigo) throws EntityNotFoundException, IOException {
        RecursoDTO recursoDTO = findByIdApi(codigo);
        return MessageResponse.success(recursoDTO);
    }

    @Override
    public ResponseEntity<RecursoDTO> findByString(@NonNull String nome) throws EntityNotFoundException, IOException {
        RecursoDTO recursoDTO = findByIdApi(nome);
        return MessageResponse.success(recursoDTO);
    }

    @Override
    public ResponseEntity<ResponseGlobalModel> delete(@NonNull Long codigo) throws IOException {
        ResponseGlobalModel response = deleteEntity(codigo);
        return MessageResponse.success(response);
    }

    private void validateRecursoModelFields(RecursoModel recursoModel) {
        Objects.requireNonNull(recursoModel.getNome(), "Nome do Recurso está nulo.");
        Objects.requireNonNull(recursoModel.getDescricao(), "Descrição do Recurso está nulo.");
    }
}
