package com.example.gym_system.controller;


import com.example.gym_system.domain.receptionist.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recepcionista")
public class ReceptionistController {

    @Autowired
    ReceptionistService service;

    @Autowired
    private ReceptionistRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> registerAReceptionist(@RequestBody @Valid DataReceptionistRegister data){
        return service.createAReceptionistRegister(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity getReceptionistById(@PathVariable Long id){
        return service.findReceptionistById(id);
    }

    @GetMapping
    public ResponseEntity listingAllReceptionists(){
        return service.getAllReceptionists();
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateAReceptionist(@RequestBody @Valid DataReceptionist dataReceptionist){
        return service.updateAnExistingReceptionist(dataReceptionist);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteAReceptionistById(@PathVariable Long id){
        return service.deleteAReceptionistById(id);
    }
}
