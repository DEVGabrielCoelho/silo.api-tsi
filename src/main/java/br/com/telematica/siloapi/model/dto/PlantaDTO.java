package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.Planta;

public class PlantaDTO extends Codigo {

	private EmpresaDTO empresa;
	private String nome;

	public PlantaDTO(EmpresaDTO empresa, String nome) {
		this.empresa = empresa;
		this.nome = nome;
	}

	public PlantaDTO(Long codigo) {
		super(codigo);
	}

	public PlantaDTO(Planta planta) {

		this.setCodigo(planta.getPlacod());
		this.empresa = new EmpresaDTO(planta.getEmpresa());
		this.nome = planta.getPlanom();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PlantaDTO [");
		if (empresa != null)
			builder.append("empresa=").append(empresa).append(", ");
		if (nome != null)
			builder.append("nome=").append(nome);
		builder.append("]");
		return builder.toString();
	}

	public EmpresaDTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaDTO empresa) {
		this.empresa = empresa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
