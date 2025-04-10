package dev.alequisk.casetecnicomeetimehubspot.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WebhookService {

    public void processWebhookEvents(List<Map<String, Object>> events) {
        for (Map<String, Object> event : events) {
            if ("contact.creation".equals(event.get("subscriptionType"))) {
                long contactId = Long.parseLong(event.get("objectId").toString());
                System.out.println("Novo contato: " + contactId);
            }
        }
    }
}