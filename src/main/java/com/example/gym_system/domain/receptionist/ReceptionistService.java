package com.example.gym_system.domain.receptionist;

import com.example.gym_system.infra.exception.NoChangeToUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.net.URI;
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

    public ResponseEntity updateAnExistingReceptionist(Long id, DataReceptionist dataReceptionist){
        if(dataReceptionist.name() == null) throw new NoChangeToUpdateException();
        Optional<Receptionist> rcp = repository.findById(id);
        if (rcp.isEmpty()) return ResponseEntity.notFound().build();
        Receptionist rcpOriginal = rcp.get();
        rcpOriginal.setName(dataReceptionist.name());
        repository.save(rcpOriginal);
        return ResponseEntity.ok().build();

    }

    public ResponseEntity getAllReceptionists(Pageable pageable){
         Page<DataListingReceptionist> listingReceptionists = repository.findAll(pageable)
                .map(receptionist -> new DataListingReceptionist(receptionist.getId(), receptionist.getName()));
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
