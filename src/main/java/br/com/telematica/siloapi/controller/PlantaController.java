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
@Tag(name = "Planta", description = "API para gerenciamento de plantas")
public class PlantaController extends SecurityRestController {

	@Autowired
	private PlantaServInterface planta;

	@GetMapping("/v1/listar")
	@Operation(description = "Listar todas as plantas cadastradas. Retorna uma lista de todas as plantas existentes.")
	public ResponseEntity<List<PlantaDTO>> getPlanta() throws IOException {
		return planta.findAllPlantaDTO();
	}

	@PostMapping("/v1/cadastrar")
	@Operation(description = "Cadastrar uma nova planta. Recebe os detalhes da planta e a armazena no sistema.")
	public ResponseEntity<PlantaDTO> createPlanta(@RequestBody PlantaModel plantaDto) throws IOException {
		return planta.save(plantaDto);
	}

	@PutMapping("/v1/atualizar/{codigo}")
	@Operation(description = "Atualizar uma planta existente. Atualiza os detalhes de uma planta com base no código fornecido.")
	public ResponseEntity<PlantaDTO> updatePlanta(@PathVariable Long codigo, PlantaModel plantaDto) throws IOException {
		return planta.update(codigo, plantaDto);
	}

	@DeleteMapping("/v1/deletar/{codigo}")
	@Operation(description = "Deletar uma planta existente. Remove uma planta específica com base no código fornecido.")
	public ResponseEntity<PlantaDTO> deletePlanta(@PathVariable Long codigo) throws IOException {
		return planta.deleteByPlacod(codigo);
	}
}
