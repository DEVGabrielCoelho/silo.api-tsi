package br.com.telematica.siloapi.model.dto;

public class PlantaDTO {

    private Integer codigo;
    private Integer codigoEmpresa;
    private String nome;

    public PlantaDTO() {
    }

    public PlantaDTO(Integer codigo, Integer codigoEmpresa, String nome) {
        this.codigo = codigo;
        this.codigoEmpresa = codigoEmpresa;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(Integer codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
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
        builder.append("PlantaDTO [codigo=");
        builder.append(codigo);
        builder.append(", codigoEmpresa=");
        builder.append(codigoEmpresa);
        builder.append(", nome=");
        builder.append(nome);
        builder.append("]");
        return builder.toString();
    }

}
