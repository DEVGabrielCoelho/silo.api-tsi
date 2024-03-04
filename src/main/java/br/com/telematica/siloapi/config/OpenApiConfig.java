package br.com.telematica.siloapi.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.classic.Logger;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

/**
 * OpenApiConfig
 */

@Configuration
public class OpenApiConfig {

	private static Logger logger = (Logger) LoggerFactory.getLogger(OpenApiConfig.class);

	@Autowired
	private BuildProperties env;

	@Bean
	public OpenAPI myOpenAPI() {
		String artifactId = env.getArtifact();
		String groupId = env.getArtifact();
		String version = env.getVersion();

		logger.info("-> Info OpenApi Config, artifactId -" + artifactId + ", groupId - " + groupId + ", version - " + version);

		String urlLocal = "http://localhost:8080";
		String urlVersion = String.format("http://localhost:8080/siloapi-%s", version);

		List<Server> servers = new ArrayList<>();
		servers.add(new Server().url(urlLocal).description("Local Environment"));
		servers.add(new Server().url(urlVersion).description("Production Server"));

		Server devServer = new Server();
		devServer.setUrl(urlLocal);
		devServer.setDescription("Server URL in Development environment");

		Server prodServer = new Server();
		prodServer.setUrl(urlVersion);
		prodServer.setDescription("Server URL in Production environment");

		Info info = new Info().title("Silo API").version(version).description("Silo Api backend.");

		return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
	}

}