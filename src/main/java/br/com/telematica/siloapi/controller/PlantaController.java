package br.com.telematica.siloapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.interfaces.SecurityRestController;
import br.com.telematica.siloapi.service.PlantaService;
import br.com.telematica.siloapi.utils.error.GenericResponseModel;
import br.com.telematica.siloapi.utils.error.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/planta")
@Tag(name = "Planta", description = "Planta API")
public class PlantaController implements SecurityRestController {

    @Autowired
    private PlantaService plantaService;

    @GetMapping("/listaPlantas")
    @Operation(description = "Busca pelas plantas cadastradas")
    public ResponseEntity<GenericResponseModel> getPlanta(@Valid @PathVariable Long id) {
        try {
            var entity = plantaService.findAllPlantaDTO();
            return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity),
                    HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse
                    .exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cadastraPlanta")
    @Operation(description = "Cadastro de uma nova planta")
    public ResponseEntity<GenericResponseModel> createPlanta(@Valid @RequestBody PlantaDTO planta) {
        try {
            var entity = plantaService.save(planta);
            return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity),
                    HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse
                    .exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/atualizaPlanta")
    @Operation(description = "Atualização de uma planta")
    public ResponseEntity<GenericResponseModel> updatePlanta(@Valid @RequestBody PlantaDTO planta) {
        try {
            var entity = plantaService.update(planta);
            return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, entity),
                    HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse
                    .exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletaPlanta/{codigo}")
    @Operation(description = "Deletar uma planta")
    public ResponseEntity<GenericResponseModel> deletePlanta(@Valid @PathVariable Integer codigo) {
        try {
            plantaService.deleteByPlacod(codigo);
            return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, null),
                    HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(MessageResponse.exceptionRequest400(e.getMessage(), null, null),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse
                    .exceptionRequest500("Exceção gerada ao executar o registro. " + e.getCause(), null, null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
