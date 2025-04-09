package dev.alequisk.casetecnicomeetimehubspot.controllers;

import dev.alequisk.casetecnicomeetimehubspot.services.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody Map<String, String> data) {
        try {
            return ResponseEntity.ok(contactService.createContact(data));
        } catch (HttpClientErrorException.TooManyRequests e) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit excedido");
        }
    }
}
