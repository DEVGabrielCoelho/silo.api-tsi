package br.com.telematica.siloapi.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.telematica.siloapi.model.enums.AcaoRecursoMapEnum;
import br.com.telematica.siloapi.model.enums.RecursoMapEnum;
import br.com.telematica.siloapi.model.enums.ServerMapEnum;
import br.com.telematica.siloapi.model.enums.VersaoMapEnum;

public class URLValidator {

	private static final Logger logger = LoggerFactory.getLogger(URLValidator.class);

	public static URLValidator validateURL(String url) {
		try {
			String[] parts = url.split("/");

			String serverPart = "/" + parts[1];
			String recursoPart = "/api/" + parts[3];
			String versionPart = "/" + parts[4];
			String actionPart = parts.length > 5 ? "/" + parts[5] : "";
			String consult = parts.length > 6 ? "/" + parts[6] : null;

			String server = ServerMapEnum.mapDescricaoToServer(serverPart.toUpperCase());
			String recurso = RecursoMapEnum.mapUrlToUrl(recursoPart.toUpperCase());
			RecursoMapEnum recursoEnum = RecursoMapEnum.mapUrlToRecursoMapEnum(recursoPart.toUpperCase());
			String version = VersaoMapEnum.mapDescricaoToVersao(versionPart.toUpperCase());
			String action = AcaoRecursoMapEnum.mapDescricaoToAction(actionPart.toUpperCase());

			if (server == null || recurso == null || version == null) {
				return new URLValidator(null, "URL inválida!");
			}

			if (consult != null || !actionPart.equals("")) {
//				logger.info(consult + " - " + !actionPart.equals("") + " - " + actionPart);
				return new URLValidator(recursoEnum, "BUSCAR");
			}

			return new URLValidator(recursoEnum, "URL válida! Server: " + server + ", Recurso: " + recurso + ", Versão: " + version + ", Ação: " + action);
		} catch (Exception e) {
			logger.error("Erro ao validar URL: " + url, e);
			return new URLValidator(null, "Erro ao processar a URL!");
		}
	}

	private RecursoMapEnum recursoMapEnum;
	private String message;

	public URLValidator(RecursoMapEnum recursoMapEnum, String message) {
		super();
		this.recursoMapEnum = recursoMapEnum;
		this.message = message;
	}

	public RecursoMapEnum getRecursoMapEnum() {
		return recursoMapEnum;
	}

	public void setRecursoMapEnum(RecursoMapEnum recursoMapEnum) {
		this.recursoMapEnum = recursoMapEnum;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
