package dev.alequisk.casetecnicomeetimehubspot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WebhookService {
    private final Logger log = LoggerFactory.getLogger(WebhookService.class);

    public void processWebhookEvents(List<Map<String, Object>> events) {
        for (Map<String, Object> event : events) {
            if ("contact.creation".equals(event.get("subscriptionType"))) {
                long contactId = Long.parseLong(event.get("objectId").toString());
                log.info("Processing contact creation event {}", contactId);
                // TODO: Ativação de ações automáticas ou envio de notificações para outros sistemas como WhatsApp, Telegram, Slack etc.
            }
        }
    }
}