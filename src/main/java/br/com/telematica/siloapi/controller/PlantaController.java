package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.List;

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

import br.com.telematica.siloapi.model.PlantaModel;
import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.services.PlantaServInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/planta")
@Tag(name = "Planta", description = "Planta API")
public class PlantaController extends SecurityRestController {

	@Autowired
	private PlantaServInterface planta;

	@GetMapping("/v1/listaPlantas")
	@Operation(description = "Busca pelas plantas cadastradas")
	public ResponseEntity<List<PlantaDTO>> getPlanta() throws IOException {
		return planta.findAllPlantaDTO();

	}

	@PostMapping("/v1/cadastraPlanta")
	@Operation(description = "Cadastro de uma nova planta")
	public ResponseEntity<PlantaDTO> createPlanta(@RequestBody PlantaModel plantaDto) throws IOException {
		return planta.save(plantaDto);
	}

	@PutMapping("/v1/atualizaPlanta/{codigo}")
	@Operation(description = "Atualização de uma planta")
	public ResponseEntity<PlantaDTO> updatePlanta(@PathVariable Long codigo, PlantaModel plantaDto) throws IOException {
		return planta.update(codigo, plantaDto);

	}

	@DeleteMapping("/v1/deletaPlanta/{codigo}")
	@Operation(description = "Deletar uma planta")
	public ResponseEntity<PlantaDTO> deletePlanta(@PathVariable Long codigo) throws IOException {
		return planta.deleteByPlacod(codigo);
	}
}
