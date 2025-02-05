package com.example.gym_system.domain.client;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String cpf;
    private LocalDate birthdate;
    private Integer age;
    private Plan plan;
    private LocalDate dateStart;
    private LocalDate dateEnd;


    public Client(DataClientRegister data) {
        this.name = data.name();
        this.phone = data.phone();
        this.cpf = data.cpf();
        this.birthdate = data.birthdate();
        this.age = Period.between(birthdate, LocalDate.now()).getYears();
        this.plan = data.plan();
        this.dateStart = LocalDate.now();
        this.dateEnd = LocalDate.now().plusMonths(1);
    }
}
