package dev.alequisk.casetecnicomeetimehubspot.services;

import dev.alequisk.casetecnicomeetimehubspot.configs.HubSpotConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ContactService {

    private final HubSpotConfig config;
    private final RestTemplate restTemplate;

    private String accessToken;

    public ContactService(HubSpotConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    public void setAccessToken(String token) {
        this.accessToken = token;
    }

    public Map<String, Object> createContact(Map<String, String> data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> properties = new HashMap<>();
        properties.put("firstname", data.get("firstname"));
        properties.put("lastname", data.get("lastname"));
        properties.put("email", data.get("email"));

        Map<String, Object> payload = Map.of("properties", properties);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(config.getCreateContactUrl(), request, Map.class);

        return response.getBody();
    }
}
