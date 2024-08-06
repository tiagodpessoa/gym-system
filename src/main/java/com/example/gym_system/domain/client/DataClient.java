package com.example.gym_system.domain.client;



import java.time.LocalDate;


public record DataClient(
        Long id,
        String name,
        String phone,
        String cpf,
        LocalDate birthdate,
        Plan plan,
        LocalDate validUntil
) {
}
