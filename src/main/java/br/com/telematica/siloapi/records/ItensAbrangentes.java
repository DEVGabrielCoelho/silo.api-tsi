package br.com.telematica.siloapi.records;

import java.util.List;

import br.com.telematica.siloapi.model.dto.EmpresaDTO;

public record ItensAbrangentes(List<EmpresaDTO> empresas) {

}
