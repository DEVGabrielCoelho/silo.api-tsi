package br.com.telematica.siloapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Silo")
public class SiloDTO {

    @Schema(description = "Código", example = "1", required = true)
    private Integer codigo;
    @Schema(description = "Tipo", example = "1", required = false)
    private Integer tipo_cilo;
    @Schema(description = "Código Planta", example = "1", required = true)
    private Integer codi_planta;
    @Schema(description = "Nome", example = "Silo 1", required = true)
    private String nome;

    public SiloDTO() {
    }

    public SiloDTO(Integer codigo, Integer tipo_cilo, Integer codi_planta, String nome) {
        this.codigo = codigo;
        this.tipo_cilo = tipo_cilo;
        this.codi_planta = codi_planta;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getTipo_cilo() {
        return tipo_cilo;
    }

    public void setTipo_cilo(Integer tipo_cilo) {
        this.tipo_cilo = tipo_cilo;
    }

    public Integer getCodi_planta() {
        return codi_planta;
    }

    public void setCodi_planta(Integer codi_planta) {
        this.codi_planta = codi_planta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SiloDTO [codigo=");
        builder.append(codigo);
        builder.append(", tipo_cilo=");
        builder.append(tipo_cilo);
        builder.append(", codi_planta=");
        builder.append(codi_planta);
        builder.append(", nome=");
        builder.append(nome);
        builder.append("]");
        return builder.toString();
    }
    
    

}
