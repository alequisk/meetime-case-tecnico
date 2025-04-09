package dev.alequisk.casetecnicomeetimehubspot.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hubspot")
public class HubSpotConfig {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String[] scopes;


    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String[] getScopes() {
        return scopes;
    }

    public void setScopes(String[] scopes) {
        this.scopes = scopes;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAuthUrl() {
        return "https://app.hubspot.com/oauth/authorize";
    }

    public String getTokenUrl() {
        return "https://api.hubapi.com/oauth/v1/token";
    }

    public String getCreateContactUrl() {
        return "https://api.hubapi.com/crm/v3/objects/contacts";
    }
}
