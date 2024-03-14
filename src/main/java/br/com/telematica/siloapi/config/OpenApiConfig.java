package br.com.telematica.siloapi.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.telematica.siloapi.utils.error.GenericResponseModel;
import ch.qos.logback.classic.Logger;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * OpenApiConfig
 */

@Configuration
@ApiResponse(responseCode = "200", description = "Ok", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) })
@ApiResponse(responseCode = "400", description = "Dados inválidos")
@ApiResponse(responseCode = "403", description = "Não Autorizado")
@ApiResponse(responseCode = "500", description = "Erro no servidor", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) })

public class OpenApiConfig {

	private static Logger logger = (Logger) LoggerFactory.getLogger(OpenApiConfig.class);

	@Autowired
	BuildProperties env;

	@Bean
	public OpenAPI myOpenAPI() {
//		String artifactId = env.getArtifact();
//		String groupId = env.getArtifact();
		String version = env.getVersion();

//		logger.info("-> Info OpenApi Config, artifactId -" + artifactId);
//		logger.info("-> Info OpenApi Config, groupId - " + groupId);
		logger.info("-> Info OpenApi Config, version - " + version);

		Info info = new Info().title("Silo API").version(version).description("Silo Api backend.");

//		return new .securitySchemes(Arrays.asList(new SecurityScheme[] { new ApiKeyScheme("bearerAuth", "Authorization", "header") }))
//				.securityContexts(Arrays.asList(new SecurityContext[] { SecurityContext.builder().securityReferences(Arrays.asList(new SecurityReference("bearerAuth", new AuthorizationScopes[] { new AuthorizationScope("global", "accessEverything") }))).forPaths(PathSelectors.any()).build() }));
		return new OpenAPI().info(info);
	}

}