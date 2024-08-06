package com.example.gym_system.domain.client;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record DataClientRegister(
        @NotBlank
        String name,
        @NotBlank
        String phone,
        @NotBlank
        String cpf,
        @NotNull
        @Past
        LocalDate birthdate,
        @NotNull
        Plan plan
) {
}
