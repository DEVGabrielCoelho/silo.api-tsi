package br.com.telematica.siloapi.records;

import java.util.List;

import br.com.telematica.siloapi.model.dto.EmpresaDTO;
import br.com.telematica.siloapi.model.dto.PlantaDTO;
import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.model.dto.SiloModuloDTO;
import br.com.telematica.siloapi.model.dto.TipoSiloDTO;

public record ItensAbrangentes(List<EmpresaDTO> empresas, List<PlantaDTO> planta, List<TipoSiloDTO> tipoSilo, List<SiloDTO> silo, List<SiloModuloDTO> modulo) {

}
