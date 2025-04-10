package dev.alequisk.casetecnicomeetimehubspot.services;

import dev.alequisk.casetecnicomeetimehubspot.configs.HubSpotConfig;
import dev.alequisk.casetecnicomeetimehubspot.dtos.TokenResponse;
import dev.alequisk.casetecnicomeetimehubspot.exceptions.InvalidTokenException;
import dev.alequisk.casetecnicomeetimehubspot.exceptions.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AuthService {
    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final HubSpotConfig config;
    private final RestTemplate restTemplate;

    public AuthService(HubSpotConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    public String generateAuthorizationUrl() {
        return UriComponentsBuilder.fromHttpUrl(config.getAuthUrl())
                .queryParam("client_id", config.getClientId())
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", config.getScopes())
                .queryParam("response_type", "code")
                .toUriString();
    }

    public TokenResponse exchangeCodeForToken(String code) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

            body.add("grant_type", "authorization_code");
            body.add("client_id", config.getClientId());
            body.add("client_secret", config.getClientSecret());
            body.add("redirect_uri", config.getRedirectUri());
            body.add("code", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(config.getTokenUrl(), request, TokenResponse.class);

            return response.getBody();
        } catch (HttpClientErrorException.BadRequest e) {
            log.info("Unauthorized exception: {}", e.getMessage());
            throw new UnauthorizedException("Unauthorized");
        }
    }

    public TokenResponse executeRefreshToken(String refreshToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

            body.add("grant_type", "refresh_token");
            body.add("refresh_token", refreshToken);
            body.add("client_id", config.getClientId());
            body.add("client_secret", config.getClientSecret());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(config.getTokenUrl(), request, TokenResponse.class);

            return response.getBody();
        } catch (HttpClientErrorException.BadRequest e) {
            log.info("Invalid refresh token: {}", e.getMessage());
            throw new InvalidTokenException("Access token is invalid");
        }
    }
}
