package br.com.telematica.siloapi.model.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum RecursoMapEnum {
	SIRENE("SIRENE", "/api/sirene"), MODULO("MODULO", "/api/sirene-modulo"), PENDENCIA("PENDENCIA", "/api/pendencia"), FIRMWARE("FIRMWARE", "/api/pendencia-firmware"), MEDICAO("MEDICAO", "/api/medicao"), AUDIO("AUDIO", "/api/medicao-audio"), LOGGER("LOGGER", "/api/logger"),
	EMPRESA("EMPRESA", "/api/empresa"), CANAL("CANAL", "/api/canal"), BARRAGEM("BARRAGEM", "/api/barragem"), USUARIO("USUARIO", "/api/usuario"), PERFIL("PERFIL", "/api/perfil"), RECURSO("RECURSO", "/api/recurso"), ABRANGENCIA("ABRANGENCIA", "/api/abrangencia"),
	MODULODEVICE("ABRANGENCIA", "/api/modulo-device");

	private static final Logger logger = LoggerFactory.getLogger(RecursoMapEnum.class);
	private final String nome;
	private final String url;

	RecursoMapEnum(String nome, String url) {
		this.nome = nome;
		this.url = url;
	}

	public static Logger getLogger() {
		return logger;
	}

	public String getNome() {
		return nome;
	}

	public String getUrl() {
		return url;
	}

	public static String mapDescricaoToNome(String descricao) {
		for (RecursoMapEnum du : RecursoMapEnum.values()) {
			if (du.getNome().equalsIgnoreCase(descricao)) {
				return du.getNome();
			}
		}
		logger.error("Descrição não mapeada: " + descricao);
		return null;
	}

	public static String mapUrlToUrl(String url) {
		for (RecursoMapEnum du : RecursoMapEnum.values()) {
			if (du.getUrl().equalsIgnoreCase(url)) {
				return du.url;
			}
		}
		logger.error("URL não mapeada: " + url);
		return null;
	}

	public static RecursoMapEnum mapUrlToRecursoMapEnum(String url) {
		for (RecursoMapEnum du : RecursoMapEnum.values()) {
			if (du.getUrl().equalsIgnoreCase(url)) {
				return du;
			}
		}
		logger.error("URL não mapeada: " + url);
		return null;
	}
}
