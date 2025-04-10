package dev.alequisk.casetecnicomeetimehubspot.services;

import dev.alequisk.casetecnicomeetimehubspot.configs.HubSpotConfig;
import dev.alequisk.casetecnicomeetimehubspot.exceptions.InternalApiException;
import dev.alequisk.casetecnicomeetimehubspot.exceptions.RateLimitReachException;
import dev.alequisk.casetecnicomeetimehubspot.exceptions.UnauthorizedException;
import dev.alequisk.casetecnicomeetimehubspot.dtos.CreateContactRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ContactService {

    private final HubSpotConfig config;
    private final RestTemplate restTemplate;

    public ContactService(HubSpotConfig config) {
        this.config = config;
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> createContact(CreateContactRequest contact, String authToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", authToken);

            Map<String, Object> properties = new HashMap<>();
            properties.put("firstname", contact.firstname());
            properties.put("lastname", contact.lastname());
            properties.put("email", contact.email());

            Map<String, Object> payload = Map.of("properties", properties);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(config.getCreateContactUrl(), request, Map.class);

            return response.getBody();
        } catch (HttpClientErrorException.TooManyRequests e) {
            throw new RateLimitReachException("Rate limit exceeded");
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new UnauthorizedException("Unauthorized");
        } catch (Exception e) {
            throw new InternalApiException("An error occurred while trying create a contact");
        }
    }
}
