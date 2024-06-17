package br.com.telematica.siloapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	private static Logger logger = LoggerFactory.getLogger(OpenApiConfig.class);

	@Autowired
	BuildProperties env;

	@Bean
	public OpenAPI myOpenAPI() {
		String version = env.getVersion();
		String name = env.getName();
//		Gson gson = new Gson();
		logger.info("Info OpenApi Config, Name Package: " + env.getArtifact() + ", version - " + version + " - Name: " + name);

//		logger.info(gson.toJson(env));

		Info info = new Info().title(name).version(version).description("Sirene Api backend.");
		return new OpenAPI().info(info);
	}
}
