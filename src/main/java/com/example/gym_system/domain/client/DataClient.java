package com.example.gym_system.domain.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DataClient(
        @NotNull
        Long id,
        String name,
        String phone,
        String cpf,
        LocalDate birthdate,
        Plan plan
) {
}
