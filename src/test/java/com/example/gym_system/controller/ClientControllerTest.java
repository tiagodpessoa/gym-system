package com.example.gym_system.controller;

import com.example.gym_system.domain.client.*;
import com.example.gym_system.domain.user.User;
import com.example.gym_system.infra.security.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ClientControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClientRepository repository;

    @Autowired
    private WebTestClient webTestClient;

    private String authToken;

    private Client client;

    @BeforeEach
    void setUp() {
        var authenticationToken = new UsernamePasswordAuthenticationToken("tiago", "123");
        var authentication = manager.authenticate(authenticationToken);
        authToken = tokenService.generateToken((User) authentication.getPrincipal());

        client = createAClient("Tiago", "48984719321",
                "66735624866", LocalDate.of(2003, 11, 8), Plan.MONTHLY);
    }


    @Test
    void shouldReturnCode400ForUpdateWithoutChanges() {

        String json = "{}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);

        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/cliente/1",
                HttpMethod.PUT,
                new HttpEntity<>(json, headers),
                Void.class
        );

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void shouldReturnCode200ForUpdateWithSomeChanges() {

        String json = "{" +
                "\"name\": \"juan\"," +
                "\"birthdate\": \"2003-11-08\"" +
                "}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);

        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/cliente/1",
                HttpMethod.PUT,
                new HttpEntity<>(json, headers),
                Void.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void shouldUpdateClientEndDateOneMonthLater(){

        webTestClient.patch()
                .uri("/cliente/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    var updatedClient = repository.findById(client.getId()).get();
                    Assertions.assertEquals(updatedClient.getDateEnd(), LocalDate.now().plusMonths(1));
                });
    }

    private Client createAClient(String name, String phone, String cpf, LocalDate birthdate, Plan plan) {
        var client = new Client(new DataClientRegister(name, phone, cpf, birthdate, plan));
        repository.save(client);
        return client;
    }

}
