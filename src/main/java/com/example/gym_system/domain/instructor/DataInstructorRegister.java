package com.example.gym_system.domain.instructor;

import jakarta.validation.constraints.NotBlank;

public record DataInstructorRegister(

        @NotBlank
        String name,
        @NotBlank
        String cref
) {
}
