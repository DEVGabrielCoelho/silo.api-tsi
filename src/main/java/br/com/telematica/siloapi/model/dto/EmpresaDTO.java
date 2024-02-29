package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.EmpresaEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Empresa")
public class EmpresaDTO {

    @Schema(description = "Código", example = "1", required = true)
    private Integer codigo;
    @Schema(description = "Nome", example = "Empresa 1", required = true)
    private String nome;
    @Schema(description = "CNPJ", example = "12345678901234", required = true)
    private Long cnpj;

    public EmpresaDTO() {
    }

    public EmpresaDTO(Integer codigo, String nome, Long cnpj) {
        this.codigo = codigo;
        this.nome = nome;
        this.cnpj = cnpj;
    }

    public EmpresaDTO(EmpresaEntity empresa) {
        this.codigo = empresa.getEmpcod();
        this.nome = empresa.getEmpnom();
        this.cnpj = empresa.getEmpcnp();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EmpresaDTO [codigo=");
        builder.append(codigo);
        builder.append(", nome=");
        builder.append(nome);
        builder.append(", cnpj=");
        builder.append(cnpj);
        builder.append("]");
        return builder.toString();
    }

}
