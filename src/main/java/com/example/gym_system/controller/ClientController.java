package com.example.gym_system.controller;

import com.example.gym_system.domain.client.ClientService;
import com.example.gym_system.domain.client.DataClient;
import com.example.gym_system.domain.client.DataClientRegister;
import com.example.gym_system.infra.exception.NoChangeToUpdateException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@SecurityRequirement(name = "bearer-key")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping
    public ResponseEntity getAllClients(@PageableDefault(size = 30, sort = {"name"}) Pageable pageable){
        return service.getAllClients(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAClientById(@PathVariable Long id){
        return service.getAClientById(id);
    }

    @PostMapping
    @Transactional
    public ResponseEntity createANewClient(@Valid @RequestBody DataClientRegister data){
        return service.createAClient(data);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateAClient(@PathVariable Long id, @RequestBody @Valid DataClient dataClient) {
        return service.updateAnExistingClient(id, dataClient);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteAClient(@PathVariable Long id){
        return service.deleteAClientById(id);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity clientPayment(@PathVariable Long id){
        return service.toPay(id);
    }
}
