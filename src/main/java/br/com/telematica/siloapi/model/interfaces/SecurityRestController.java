package br.com.telematica.siloapi.model.interfaces;

import br.com.telematica.siloapi.utils.error.GenericResponseModel;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@ApiResponses(value = { @ApiResponse(responseCode = "400", description = "Dados inválidos", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) }),
		@ApiResponse(responseCode = "403", description = "Não Autorizado", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) }),
		@ApiResponse(responseCode = "404", description = "Não Encontrado", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) }),
		@ApiResponse(responseCode = "500", description = "Erro no servidor", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponseModel.class)) }) })
public interface SecurityRestController {

}
