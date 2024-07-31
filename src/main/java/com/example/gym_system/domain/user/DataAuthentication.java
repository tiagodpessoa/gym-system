package com.example.gym_system.domain.user;

import jakarta.validation.constraints.NotBlank;

public record DataAuthentication(
        @NotBlank
        String login,
        @NotBlank
        String password
) {
}
