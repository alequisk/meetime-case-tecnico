package dev.alequisk.casetecnicomeetimehubspot.controllers;


import dev.alequisk.casetecnicomeetimehubspot.dtos.RefreshTokenRequest;
import dev.alequisk.casetecnicomeetimehubspot.dtos.TokenResponse;
import dev.alequisk.casetecnicomeetimehubspot.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para autorização e callback com HubSpot.")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "Obter URL de autorização",
            description = "Gera e retorna a URL para autorização com HubSpot. Utilize esta URL para iniciar o fluxo de autenticação."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "URL gerada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/url")
    public ResponseEntity<Map<String, String>> getAuthorizationUrl() {
        return ResponseEntity.ok(Map.of("authorization_url", authService.generateAuthorizationUrl()));
    }

    @Operation(
            summary = "Receber callback de autorização",
            description = "Recebe o código de autorização retornado pelo HubSpot e troca-o por tokens de acesso. O parâmetro " +
                    "`code` deve ser informado conforme retornado na URL de callback."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens gerados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @GetMapping("/callback")
    public ResponseEntity<?> handleCallback(
            @RequestParam
            @Parameter(description = "Código de autorização retornado pelo HubSpot", example = "abc123")
            String code) {
        return ResponseEntity.ok(authService.exchangeCodeForToken(code));
    }

    @Operation(
            summary = "Atualizar o Token de Acesso",
            description = "Recebe um refresh token e retorna os novos tokens de acesso."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens renovados com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenRequest.class))),
            @ApiResponse(responseCode = "400", description = "Refresh token inválido")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest body) {
        return ResponseEntity.ok(authService.executeRefreshToken(body.refreshToken()));
    }
}
