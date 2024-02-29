package br.com.telematica.siloapi.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import br.com.telematica.siloapi.model.interfaces.SecurityRestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/empresa")
@Tag(name = "Empresa", description = "Empresa API")
public class EmpresaController implements SecurityRestController {

    @GetMapping("/{id}")
    public String getEmpresa(@PathVariable Long id) {
        // implementation
        return "Get empresa with id: " + id;
    }

    @PostMapping
    public String createEmpresa(@RequestBody EmpresaDTO empresa) {
        // implementation
        return "Create empresa";
    }

    @PutMapping("/{id}")
    public String updateEmpresa(@PathVariable Long id, @RequestBody EmpresaDTO empresa) {
        // implementation
        return "Update empresa with id: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteEmpresa(@PathVariable Long id) {
        // implementation
        return "Delete empresa with id: " + id;
    }

}
