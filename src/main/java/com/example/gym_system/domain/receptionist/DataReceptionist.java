package com.example.gym_system.domain.receptionist;

import jakarta.validation.constraints.NotBlank;

public record DataReceptionist(
        Long id,
        @NotBlank
        String name
) {
}
