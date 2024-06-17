package br.com.telematica.siloapi.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UrlPermissoesDTO {
	List<String> buscar = new ArrayList<>();
	List<String> deletar = new ArrayList<>();
	List<String> criar = new ArrayList<>();
	List<String> editar = new ArrayList<>();
	List<String> perfil = new ArrayList<>();

	public void limparPermissao() {
		buscar.clear();
		deletar.clear();
		criar.clear();
		editar.clear();
		perfil.clear();
	}

	public void adicionarPermissao(String metodo, String url) {
		switch (metodo) {
		case "GET":
			buscar.add(url);
			break;
		case "DELETE":
			deletar.add(url);
			break;
		case "POST":
			criar.add(url);
			break;
		case "PUT":
			editar.add(url);
			break;
		}
	}

	public String[] getBuscar() {
		return buscar.toArray(new String[0]);
	}

	public String[] getDeletar() {
		return deletar.toArray(new String[0]);
	}

	public String[] getCriar() {
		return criar.toArray(new String[0]);
	}

	public String[] getEditar() {
		return editar.toArray(new String[0]);
	}

	public String[] getPerfil() {
		return perfil.toArray(new String[0]);
	}

	public void setPerfil(PerfilDTO listaPerfil) {
		perfil.add(listaPerfil.getDescricao().toUpperCase());
		System.out.println(perfil.toString());
	}

	public void setPerfil(List<PerfilDTO> listaPerfil) {
		for (PerfilDTO per : listaPerfil) {
			perfil.add(per.getDescricao().toUpperCase());
		}
		System.out.println(perfil.toString());
	}

	public void setPerfil(String str) {
		perfil.add(str);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UrlPermissoesDTO [ \n 	urlGet    =");
		builder.append(buscar);
		builder.append(", \n 	urlDelete =");
		builder.append(deletar);
		builder.append(",\n 	urlPost   =");
		builder.append(criar);
		builder.append(",\n 	urlPut    =");
		builder.append(editar);
		builder.append(",\n 	perfil	  =");
		builder.append(perfil);
		builder.append("\n]");
		return builder.toString();
	}
}
