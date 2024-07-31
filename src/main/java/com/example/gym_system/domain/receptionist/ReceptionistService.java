package com.example.gym_system.domain.receptionist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ReceptionistService {

    @Autowired
    private ReceptionistRepository repository;

    public ResponseEntity createAReceptionistRegister(DataReceptionistRegister data){
        var receptionist = new Receptionist(data);
        repository.save(receptionist);
        URI location = URI.create("http://localhost:8080/recepcionista/" + receptionist.getId());
        return ResponseEntity.created(location).build();
    }

    public ResponseEntity updateAnExistingReceptionist(DataReceptionist dataReceptionist){
        Optional<Receptionist> rcp = repository.findById(dataReceptionist.id());
        if (rcp.isEmpty()) return ResponseEntity.notFound().build();
        Receptionist rcpOriginal = rcp.get();
        if(dataReceptionist.name() != null) rcpOriginal.setName(dataReceptionist.name());
        repository.save(rcpOriginal);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity getAllReceptionists(){
         List<DataListingReceptionist> listingReceptionists = repository.findAll()
                .stream()
                .map(receptionist -> new DataListingReceptionist(receptionist.getId(), receptionist.getName())).toList();
         return ResponseEntity.ok(listingReceptionists);
    }

    public ResponseEntity deleteAReceptionistById(Long id){
        if(!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity findReceptionistById(Long id){
        return ResponseEntity.of((repository.findById(id).map(rcp -> new DataReceptionist(
                rcp.getId(),
                rcp.getName()))));
    }


}
