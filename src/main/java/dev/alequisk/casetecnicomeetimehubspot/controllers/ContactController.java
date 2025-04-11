package dev.alequisk.casetecnicomeetimehubspot.controllers;

import dev.alequisk.casetecnicomeetimehubspot.dtos.CreateContactRequest;
import dev.alequisk.casetecnicomeetimehubspot.services.ContactService;
import io.github.bucket4j.Bucket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
@Tag(name = "Contatos", description = "Endpoints para gerenciamento de contatos")
public class ContactController {

    private final ContactService contactService;
    private Bucket bucket;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostConstruct
    public void setupBucket() {
        this.bucket = Bucket.builder()
                .addLimit(limit -> limit.capacity(80).refillGreedy(80, Duration.ofSeconds(10)))
                .build();
    }

    @Operation(
            summary = "Criar contato",
            description = "Cria um contato no HubSpot com base nos dados fornecidos."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contato criado com sucesso"),
            @ApiResponse(responseCode = "429", description = "Limite de requisições excedido"),
            @ApiResponse(responseCode = "401", description = "Não possui autorização para criar o recurso")
    })
    @PostMapping
    public ResponseEntity<?> createContact(
            @Parameter(description = "Token de autorização Bearer", required = true)
            @RequestHeader("Authorization") String bearerToken,
            @Parameter(description = "Dados do contato", required = true)
            @RequestBody CreateContactRequest data) {
        if (bucket.tryConsume(1)) {
            Map<String, Object> contact = contactService.createContact(data, bearerToken);
            return ResponseEntity.ok(contact);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
}
