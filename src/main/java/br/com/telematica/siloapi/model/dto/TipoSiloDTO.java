package br.com.telematica.siloapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo Silo")
public class TipoSiloDTO {

    @Schema(description = "Código", example = "1", required = true)
    private Integer codigo;
    @Schema(description = "Código da Empresa", example = "1", required = true)
    private Integer empresa;
    @Schema(description = "Descrição", example = "Tipo 1", required = true)
    private String descricao;

}
