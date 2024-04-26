package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.EmpresaEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Empresa")
public class EmpresaDTO extends Codigo {

	@Schema(description = "Nome", example = "Empresa 1", nullable = true)
	private String nome;
	@Schema(description = "CNPJ", example = "12345678901234", nullable = true)
	private Long cnpj;

	public EmpresaDTO() {
	}

	public EmpresaDTO(Long codigo, String nome, Long cnpj) {
		super(codigo);
		this.nome = nome;
		this.cnpj = cnpj;
	}

	public EmpresaDTO(EmpresaEntity empresa) {
		super();
		this.setCodigo(empresa.getEmpcod());
		this.nome = empresa.getEmpnom();
		this.cnpj = empresa.getEmpcnp();
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
		builder.append("EmpresaDTO  codigo=");
		builder.append(getCodigo());
		builder.append("[ nome=");
		builder.append(nome);
		builder.append(", cnpj=");
		builder.append(cnpj);
		builder.append("]");
		return builder.toString();
	}

}
