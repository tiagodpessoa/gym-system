package com.example.gym_system.domain.instructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository repository;

    public ResponseEntity createAInstructor(DataInstructorRegister data){
        var newInstructor = new Instructor(data);
        repository.save(newInstructor);
        URI location = URI.create("http://localhost:8080/instrutor/" + newInstructor.getId());
        return ResponseEntity.created(location).build();
    }

    public ResponseEntity updateAnExistingInstructor(DataInstructor data){
        Optional<Instructor> originalInstructor = repository.findById(data.id());
        if(originalInstructor.isPresent()) {
            Instructor instructorUpdated = originalInstructor.get();
            if (data.name() != null) instructorUpdated.setName(data.name());
            if (data.cref() != null) instructorUpdated.setCref(data.cref());
            repository.save(instructorUpdated);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public List<DataInstructor> findAllInstructors(){
        return repository.findAll().stream()
                .map(instructor -> new DataInstructor(instructor.getId(), instructor.getName(), instructor.getCref()))
                .toList();
    }

    public Optional<DataInstructor> findAInstructorById(Long id) {
        return repository.findById(id)
                .map(instructor -> new DataInstructor(instructor.getId(), instructor.getName(), instructor.getCref()));
    }

    public ResponseEntity deleteAnInstructorById(Long id){
        if(!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
