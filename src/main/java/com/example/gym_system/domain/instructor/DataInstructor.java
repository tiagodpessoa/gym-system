package com.example.gym_system.domain.instructor;

import jakarta.validation.constraints.NotNull;

public record DataInstructor(
        @NotNull
        Long id,
        String name,
        String cref
) {
}
