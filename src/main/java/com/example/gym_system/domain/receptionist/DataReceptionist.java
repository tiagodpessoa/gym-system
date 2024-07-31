package com.example.gym_system.domain.receptionist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataReceptionist(
        @NotNull
        Long id,
        @NotBlank
        String name
) {
}
