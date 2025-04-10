package dev.alequisk.casetecnicomeetimehubspot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshTokenRequest(
        @JsonProperty("refresh_token")
        String refreshToken
) { }
