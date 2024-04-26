package br.com.telematica.siloapi.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.Logger;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * OpenApiConfig
 */

@Configuration
public class OpenApiConfig {

	private static Logger logger = (Logger) LoggerFactory.getLogger(OpenApiConfig.class);

	@Autowired
	BuildProperties env;

	@Bean
	public OpenAPI myOpenAPI() {

		String version = env.getVersion();

		logger.info("-> Info OpenApi Config, version - " + version);

		Info info = new Info().title("Silo API").version(version).description("Silo Api backend.");

		return new OpenAPI().info(info);
	}

}