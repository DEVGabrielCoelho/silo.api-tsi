package br.com.telematica.siloapi.model.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UrlPermissoesDTO {
	List<String> urlGet = new ArrayList<>();
	List<String> urlDelete = new ArrayList<>();
	List<String> urlPost = new ArrayList<>();
	List<String> urlPut = new ArrayList<>();
	List<String> perfil = new ArrayList<>();

	public void limparPermissao() {
		urlGet.clear();
		urlDelete.clear();
		urlPost.clear();
		urlPut.clear();
		perfil.clear();
	}

	public void adicionarPermissao(String metodo, String url) {
		switch (metodo) {
		case "GET":
			urlGet.add(url);
			break;
		case "DELETE":
			urlDelete.add(url);
			break;
		case "POST":
			urlPost.add(url);
			break;
		case "PUT":
			urlPut.add(url);
			break;
		}
	}

	public String[] getUrlGet() {
		return urlGet.toArray(new String[0]);
	}

	public String[] getUrlDelete() {
		return urlDelete.toArray(new String[0]);
	}

	public String[] getUrlPost() {
		return urlPost.toArray(new String[0]);
	}

	public String[] getUrlPut() {
		return urlPut.toArray(new String[0]);
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
		builder.append(urlGet);
		builder.append(", \n 	urlDelete =");
		builder.append(urlDelete);
		builder.append(",\n 	urlPost   =");
		builder.append(urlPost);
		builder.append(",\n 	urlPut    =");
		builder.append(urlPut);
		builder.append(",\n 	perfil	  =");
		builder.append(perfil);
		builder.append("\n]");
		return builder.toString();
	}
}
