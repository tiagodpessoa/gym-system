package com.example.gym_system.controller;

import com.example.gym_system.domain.instructor.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/instrutor")
@SecurityRequirement(name = "bearer-key")
public class InstructorController {

    @Autowired
    private InstructorService service;

    @PostMapping
    @Transactional
    public ResponseEntity registerAInstructor(@RequestBody @Valid DataInstructorRegister data) {
        return service.createAInstructor(data);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateAInstructor(@PathVariable Long id, @Valid @RequestBody DataInstructor data) {
        return service.updateAnExistingInstructor(id, data);
    }

    @GetMapping
    public ResponseEntity getAllInstructors(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable){
        return service.findAllInstructors(pageable);
    }


    @GetMapping("/{id}")
    public ResponseEntity getAInstructorById(@PathVariable Long id){
        return service.findAInstructorById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAInstructor(@PathVariable Long id){
        return service.deleteAnInstructorById(id);
    }
}
