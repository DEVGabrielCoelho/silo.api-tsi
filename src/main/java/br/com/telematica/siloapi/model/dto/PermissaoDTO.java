package br.com.telematica.siloapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Permissão")
public class PermissaoDTO {

    @Schema(description = "Código", example = "1", required = true)
    private Integer codigo;
    @Schema(description = "Descrição", example = "Administrador", required = true)
    private String descricao;
    @Schema(description = "Planta", example = "1", required = true)
    private Long planta;
    @Schema(description = "Silo", example = "1", required = true)
    private Long silo;
    @Schema(description = "Medição", example = "1", required = true)
    private Long medicao;
    @Schema(description = "Tipo Silo", example = "1", required = true)
    private Long tipo_silo;
    @Schema(description = "Empresa", example = "1", required = true)
    private Long empresa;


    public PermissaoDTO() {
    }

    public PermissaoDTO(Integer codigo, String descricao, Long planta, Long silo, Long medicao, Long tipo_silo, Long empresa) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.planta = planta;
        this.silo = silo;
        this.medicao = medicao;
        this.tipo_silo = tipo_silo;
        this.empresa = empresa;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getPlanta() {
        return planta;
    }

    public void setPlanta(Long planta) {
        this.planta = planta;
    }

    public Long getSilo() {
        return silo;
    }

    public void setSilo(Long silo) {
        this.silo = silo;
    }

    public Long getMedicao() {
        return medicao;
    }

    public void setMedicao(Long medicao) {
        this.medicao = medicao;
    }

    public Long getTipo_silo() {
        return tipo_silo;
    }

    public void setTipo_silo(Long tipo_silo) {
        this.tipo_silo = tipo_silo;
    }

    public Long getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Long empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PermissaoDTO [codigo=");
        builder.append(codigo);
        builder.append(", descricao=");
        builder.append(descricao);
        builder.append(", planta=");
        builder.append(planta);
        builder.append(", silo=");
        builder.append(silo);
        builder.append(", medicao=");
        builder.append(medicao);
        builder.append(", tipo_silo=");
        builder.append(tipo_silo);
        builder.append(", empresa=");
        builder.append(empresa);
        builder.append("]");
        return builder.toString();
    }
    
    
}
