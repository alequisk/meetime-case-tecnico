package dev.alequisk.casetecnicomeetimehubspot.controllers;

import dev.alequisk.casetecnicomeetimehubspot.services.WebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webhook/contacts")
public class WebhookController {

    private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @PostMapping
    public ResponseEntity<?> handleWebhook(@RequestBody List<Map<String, Object>> events) {
        webhookService.processWebhookEvents(events);
        return ResponseEntity.ok().build();
    }
}
