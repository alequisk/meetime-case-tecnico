package dev.alequisk.casetecnicomeetimehubspot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(@JsonProperty("token_type") String tokenType,
                            @JsonProperty("refresh_token") String refreshToken,
                            @JsonProperty("expires_in") int expiresIn,
                            @JsonProperty("access_token") String accessToken) { }