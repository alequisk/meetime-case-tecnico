package dev.alequisk.casetecnicomeetimehubspot.controllers;

import dev.alequisk.casetecnicomeetimehubspot.services.WebhookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Webhook", description = "Endpoints para processamento de webhooks de contatos")
@RestController
@RequestMapping("/webhook/contacts")
public class WebhookController {

    private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Operation(
            summary = "Processar webhook de contatos",
            description = "Processa eventos de webhook para contatos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Webhook processado com sucesso")
    })
    @PostMapping
    public ResponseEntity<?> handleWebhook(@RequestBody List<Map<String, Object>> events, @RequestHeader("X-HubSpot-Signature-v3") String xHubSpotSignatureV3 ) {
        if (xHubSpotSignatureV3 == null) {
            throw new RuntimeException("Request is missing X-HubSpot-Signature-v3");
        }

        webhookService.processWebhookEvents(events);
        return ResponseEntity.ok().build();
    }
}
