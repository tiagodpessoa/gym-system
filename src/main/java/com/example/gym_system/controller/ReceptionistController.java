package com.example.gym_system.controller;


import com.example.gym_system.domain.receptionist.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recepcionista")
public class ReceptionistController {

    @Autowired
    private ReceptionistRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> registerAReceptionist(@RequestBody @Valid DataReceptionistRegister data){
        System.out.println(data);
        var receptionist = new Receptionist(data);
        repository.save(receptionist);
        URI location = URI.create("http://localhost:8080/recepcionista/" + receptionist.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataReceptionist> getReceptionistById(@PathVariable Long id){
        return ResponseEntity.of((repository.findById(id).map(rcp -> new DataReceptionist(
                rcp.getId(),
                rcp.getName()))));
    }

    @GetMapping
    public ResponseEntity<List<DataListingReceptionist>> getAllReceptionists(){
        List<DataListingReceptionist> dataReceptionists = repository.findAll().stream()
                .map(dataRcpList -> new DataListingReceptionist(dataRcpList.getName())).collect(Collectors.toList());
        return ResponseEntity.ok(dataReceptionists);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateAReceptionist(@RequestBody @Valid DataReceptionist dataReceptionist){
        Optional<Receptionist> rcp = repository.findById(dataReceptionist.id());
        if (rcp.isEmpty()) return ResponseEntity.notFound().build();
        Receptionist rcpOriginal = rcp.get();
        if(dataReceptionist.name() != null) rcpOriginal.setName(dataReceptionist.name());
        repository.save(rcpOriginal);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteAReceptionistById(@PathVariable Long id){
        if(repository.findById(id).isEmpty()) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
