package br.com.telematica.siloapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.GenericResponseModel;
import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.service.PlantaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/planta")
@Tag(name = "Planta", description = "Planta API")
public class PlantaController extends SecurityRestController {

	@Autowired
	private PlantaService plantaService;

	@GetMapping("/listaPlantas")
	@Operation(description = "Busca pelas plantas cadastradas")
	public ResponseEntity<GenericResponseModel> getPlanta() throws IOException {
		return plantaService.findAllPlantaDTO();

	}

	@PostMapping("/cadastraPlanta")
	@Operation(description = "Cadastro de uma nova planta")
	public ResponseEntity<GenericResponseModel> createPlanta(@Valid @RequestBody PlantaDTO planta) {

		return plantaService.save(planta);

	}

	@PutMapping("/atualizaPlanta")
	@Operation(description = "Atualização de uma planta")
	public ResponseEntity<GenericResponseModel> updatePlanta(@Valid PlantaDTO planta) throws IOException {
		return plantaService.update(planta);

	}

	@DeleteMapping("/deletaPlanta/{codigo}")
	@Operation(description = "Deletar uma planta")
	public ResponseEntity<GenericResponseModel> deletePlanta(@Valid @PathVariable Integer codigo) throws IOException {
		return plantaService.deleteByPlacod(codigo);
	}
}
