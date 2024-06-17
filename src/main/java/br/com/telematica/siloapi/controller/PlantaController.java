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

import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.services.PlantaInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/planta")
@Tag(name = "Planta", description = "Planta API")
public class PlantaController extends SecurityRestController {

	@Autowired
	private PlantaInterface planta;

	@GetMapping("/v1/listaPlantas")
	@Operation(description = "Busca pelas plantas cadastradas")
	public ResponseEntity<Object> getPlanta() throws IOException {
		return planta.findAllPlantaDTO();

	}

	@PostMapping("/v1/cadastraPlanta")
	@Operation(description = "Cadastro de uma nova planta")
	public ResponseEntity<Object> createPlanta(@Valid @RequestBody PlantaDTO plantaDto) {
		return planta.save(plantaDto);
	}

	@PutMapping("/v1/atualizaPlanta")
	@Operation(description = "Atualização de uma planta")
	public ResponseEntity<Object> updatePlanta(@Valid PlantaDTO plantaDto) throws IOException {
		return planta.update(plantaDto);

	}

	@DeleteMapping("/v1/deletaPlanta/{codigo}")
	@Operation(description = "Deletar uma planta")
	public ResponseEntity<Object> deletePlanta(@Valid @PathVariable Integer codigo) throws IOException {
		return planta.deleteByPlacod(codigo);
	}
}
