package com.example.gym_system.domain.receptionist;

import jakarta.validation.constraints.NotBlank;

public record DataReceptionistRegister(
        @NotBlank
        String name
) {
}
