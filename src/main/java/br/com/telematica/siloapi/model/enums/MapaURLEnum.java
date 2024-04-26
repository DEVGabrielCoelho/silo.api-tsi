package br.com.telematica.siloapi.model.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MapaURLEnum {
//    SIRENE("/sirene.auth/api/sirene/"),
//    MODULO("/sirene.auth/api/sirene-modulo/"),
//    PENDENCIA ("/sirene.auth/api/pendencia/"),
//    FIRMWARE ("/sirene.auth/api/pendencia/firmware/"),
//    MEDICAO ("/sirene.auth/api/medicao/"),
//    AUDIO ("/sirene.auth/api/medicao/audio/"),
//    LOGGER ("/sirene.auth/api/logger/"),
//    EMPRESA ("/sirene.auth/api/empresa/"),
//    CANAL ("/sirene.auth/api/canal/"),
//    BARRAGEM ("/sirene.auth/api/barragem/"),
//    USUARIO ("/sirene.auth/api/usuario/");
//	
	SIRENE("/sirene/api/sirene/"), MODULO("/sirene/api/sirene-modulo/"), PENDENCIA("/sirene/api/pendencia/"), FIRMWARE("/sirene/api/pendencia/firmware/"), MEDICAO("/sirene/api/medicao/"), AUDIO("/sirene/api/medicao/audio/"), LOGGER("/sirene/api/logger/"), EMPRESA("/sirene/api/empresa/"),
	CANAL("/sirene/api/canal/"), BARRAGEM("/sirene/api/barragem/"), USUARIO("/sirene/api/usuario/");

	private static Logger logger = LoggerFactory.getLogger(MapaURLEnum.class);
	private final String url;
	String mapUrl = "sirene";

	MapaURLEnum(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public static String mapDescricaoToUrl(String descricao) {
		for (MapaURLEnum du : MapaURLEnum.values()) {
			if (du.name().equalsIgnoreCase(descricao)) {
				return du.getUrl();
			}
		}
		logger.error("Descrição não mapeada: " + descricao);
		return null;
	}

}
