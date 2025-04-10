package dev.alequisk.casetecnicomeetimehubspot.dtos;

public record CreateContactRequest (
        String email,
        String lastname,
        String firstname
) { }
