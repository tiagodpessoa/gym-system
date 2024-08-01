package com.example.gym_system.controller;

import com.example.gym_system.domain.client.ClientService;
import com.example.gym_system.domain.client.DataClient;
import com.example.gym_system.domain.client.DataClientRegister;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClientController {

    @Autowired
    private ClientService service;

    @PostMapping
    @Transactional
    public ResponseEntity registerAClient(@RequestBody @Valid DataClientRegister data){
        return service.createAClient(data);
    }

    @GetMapping
    public ResponseEntity listAllClients(){
        return service.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity listAllClients(@PathVariable Long id){
        return service.getAClientById(id);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteAClient(@PathVariable Long id){
        return service.deleteAClientById(id);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateAClient(@RequestBody @Valid DataClient dataClient){
        return service.updateAnExistingClient(dataClient);
    }

}
