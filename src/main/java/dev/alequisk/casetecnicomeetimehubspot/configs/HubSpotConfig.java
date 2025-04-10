package dev.alequisk.casetecnicomeetimehubspot.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hubspot")
public class HubSpotConfig {
    private static final String AUTH_URL = "https://app.hubspot.com/oauth/authorize";
    private static final String TOKEN_URL = "https://api.hubapi.com/oauth/v1/token";
    private static final String CREATE_CONTACT_URL = "https://api.hubapi.com/crm/v3/objects/contacts";

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String[] scopes;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

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

    public String getAuthUrl() {
        return AUTH_URL;
    }

    public String getTokenUrl() {
        return TOKEN_URL;
    }

    public String getCreateContactUrl() {
        return CREATE_CONTACT_URL;
    }
}
