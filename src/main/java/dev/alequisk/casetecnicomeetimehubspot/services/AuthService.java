package dev.alequisk.casetecnicomeetimehubspot.services;

import dev.alequisk.casetecnicomeetimehubspot.configs.HubSpotConfig;
import dev.alequisk.casetecnicomeetimehubspot.exceptions.MissingOrUnknownClientSecretAuthException;
import dev.alequisk.casetecnicomeetimehubspot.exceptions.MissingOrUnknownCodeAuthException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class AuthService {

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

    public Map<String, Object> exchangeCodeForToken(String code) throws MissingOrUnknownClientSecretAuthException {

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

            ResponseEntity<Map> response = restTemplate.postForEntity(config.getTokenUrl(), request, Map.class);

            return response.getBody();
        } catch (HttpClientErrorException.BadRequest e) {
            if (e.getMessage().contains("missing or invalid client secret")) {
                throw new MissingOrUnknownClientSecretAuthException(e);
            }
            throw new RuntimeException(e);
        }
    }
}
