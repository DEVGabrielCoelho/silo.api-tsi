package br.com.telematica.siloapi.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.interfaces.SecurityRestController;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1//planta")
@Tag(name = "Planta", description = "Planta API")
public class PlantaController implements SecurityRestController {

    @GetMapping("/{id}")
    public String getPlanta(@PathVariable Long id) {
        // implementation
        return "Get planta with id: " + id;
    }

    @PostMapping
    public String createPlanta(@RequestBody PlantaDTO planta) {
        // implementation
        return "Create planta";
    }

    @PutMapping("/{id}")
    public String updatePlanta(@PathVariable Long id, @RequestBody PlantaDTO planta) {
        // implementation
        return "Update planta with id: " + id;
    }

    @DeleteMapping("/{id}")
    public String deletePlanta(@PathVariable Long id) {
        // implementation
        return "Delete planta with id: " + id;
    }
}
