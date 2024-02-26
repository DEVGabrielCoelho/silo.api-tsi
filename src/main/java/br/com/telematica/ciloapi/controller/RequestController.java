package br.com.telematica.ciloapi.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.ciloapi.model.dto.RegistryDTO;
import br.com.telematica.ciloapi.model.enttity.UserEntity;
import br.com.telematica.ciloapi.model.interfaces.SecurityRestController;
import br.com.telematica.ciloapi.service.UserServices;
import br.com.telematica.ciloapi.utils.error.GenericResponseModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/request")
@Tag(name = "Request", description = "Request Controller")
public class RequestController implements SecurityRestController {

	@Autowired
	private UserServices userServices;

	@PostMapping("/register")
	public ResponseEntity<GenericResponseModel> postRegister(@Valid @RequestBody RegistryDTO entity) {
		GenericResponseModel response = new GenericResponseModel();
		try {
			UserEntity user = new UserEntity(entity.getUser(), entity.getPassword(), entity.getName(), entity.getEmail(), entity.getRole().toRole().toUpperCase());

			userServices.saveUserEncodePassword(user);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			response.setDesc("Registro já existe.");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (Exception e) {
			response.setDesc("Erro durante o salvamento.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/listUsers")
	public ResponseEntity<GenericResponseModel> getUser(){
		GenericResponseModel response = null;
		try {
			var userList = userServices.findAllRegistryDTO();
			
			response = new GenericResponseModel( 200, "Search result", new Date(), userList.size(), userList);
			return new ResponseEntity<GenericResponseModel>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error: " + e.getMessage());
			response = new GenericResponseModel( 400, "Error during search", new Date(), 0, null);
			return new ResponseEntity<GenericResponseModel>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/company")
	public String postCompany(@RequestBody String param) {
		return param;
	}

}
