package br.com.telematica.siloapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.model.interfaces.SecurityRestController;
import br.com.telematica.siloapi.utils.error.MessageResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/silo")
@Tag(name = "Silo", description = "Silo API")
public class SiloController implements SecurityRestController {

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        // implementation here
        return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, null), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody SiloDTO siloDTO) {
        return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, null), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SiloDTO siloDTO) {
        // implementation here
        return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        // implementation here
        return new ResponseEntity<>(MessageResponse.sucessRequest200("Registro feito com Sucesso", null, null), HttpStatus.OK);
    }

}
