package com.example.gym_system.controller;

import com.example.gym_system.domain.instructor.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instrutor")
public class InstructorController {

    @Autowired
    private InstructorService service;

    @PostMapping
    @Transactional
    public ResponseEntity registerAInstructor(@RequestBody @Valid DataInstructorRegister data) {
        return service.createAInstructor(data);
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateAInstructor(@RequestBody @Valid DataInstructor data) {
        return service.updateAnExistingInstructor(data);
    }

    @GetMapping
    public ResponseEntity<List<DataInstructor>> getAllInstructors(){
        return ResponseEntity.ok(service.findAllInstructors());
    }


    @GetMapping("/{id}")
    public ResponseEntity<DataInstructor> getAInstructorById(@PathVariable Long id){
        return ResponseEntity.of(service.findAInstructorById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAInstructor(@PathVariable Long id){
        return service.deleteAnInstructorById(id);
    }
}