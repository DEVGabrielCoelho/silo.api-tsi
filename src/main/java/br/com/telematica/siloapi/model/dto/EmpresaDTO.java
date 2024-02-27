package br.com.telematica.siloapi.model.dto;

public class EmpresaDTO {

    private Integer codigo;
    private String nome;
    private String cnpj;

    public EmpresaDTO() {
    }

    public EmpresaDTO(Integer codigo, String nome, String cnpj) {
        this.codigo = codigo;
        this.nome = nome;
        this.cnpj = cnpj;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
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
