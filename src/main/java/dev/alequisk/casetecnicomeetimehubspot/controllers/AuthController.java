package dev.alequisk.casetecnicomeetimehubspot.controllers;


import dev.alequisk.casetecnicomeetimehubspot.exceptions.MissingOrUnknownClientSecretAuthException;
import dev.alequisk.casetecnicomeetimehubspot.services.AuthService;
import dev.alequisk.casetecnicomeetimehubspot.services.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final ContactService contactService;

    public AuthController(AuthService authService, ContactService contactService) {
        this.authService = authService;
        this.contactService = contactService;
    }


    @GetMapping("/url")
    public ResponseEntity<Map<String, String>> getAuthorizationUrl() {
        return ResponseEntity.ok(Map.of("authorization_url", authService.generateAuthorizationUrl()));
    }

    @GetMapping("/callback")
    public ResponseEntity<?> handleCallback(@RequestParam String code) throws MissingOrUnknownClientSecretAuthException {
        Map<String, Object> tokens = authService.exchangeCodeForToken(code);
        contactService.setAccessToken((String) tokens.get("access_token"));
        return ResponseEntity.ok(tokens);
    }
}
