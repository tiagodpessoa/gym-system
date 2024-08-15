package com.example.gym_system.controller;


import com.example.gym_system.domain.receptionist.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recepcionista")
@SecurityRequirement(name = "bearer-key")
public class ReceptionistController {

    @Autowired
    ReceptionistService service;

    @PostMapping
    @Transactional
    public ResponseEntity registerAReceptionist(@RequestBody @Valid DataReceptionistRegister data){
        return service.createAReceptionistRegister(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity getReceptionistById(@PathVariable Long id){
        return service.findReceptionistById(id);
    }

    @GetMapping
    public ResponseEntity listingAllReceptionists(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable){
        return service.getAllReceptionists(pageable);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateAReceptionist(@PathVariable Long id, @Valid @RequestBody DataReceptionist dataReceptionist){
        return service.updateAnExistingReceptionist(id ,dataReceptionist);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteAReceptionistById(@PathVariable Long id){
        return service.deleteAReceptionistById(id);
    }
}
