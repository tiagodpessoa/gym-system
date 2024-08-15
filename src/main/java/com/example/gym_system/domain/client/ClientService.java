package com.example.gym_system.domain.client;

import com.example.gym_system.infra.exception.NoChangeToUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;


@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    public ResponseEntity createAClient(DataClientRegister data) {
        var newClient = new Client(data);
        repository.save(newClient);
        URI location = URI.create("http://localhost:8080/cliente/" + newClient.getId());
        return ResponseEntity.created(location).build();
    }


    public ResponseEntity getAllClients(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable)
                .map(client -> new DataListingClient(client.getId(), client.getName(), client.getAge(), client.getPhone(), client.getPlan())));
    }

    public ResponseEntity deleteAClientById(Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity getAClientById(Long id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        var dataClient = repository.findById(id).get();
        return ResponseEntity.ok(new DataClient(id, dataClient.getName(), dataClient.getPhone(), dataClient.getAge(), dataClient.getCpf(), dataClient.getBirthdate(), dataClient.getPlan(), dataClient.getDateEnd()));
    }


    public ResponseEntity updateAnExistingClient(Long id, DataClient data) {
        if(verifyDataClientToUpdate(data)) throw new NoChangeToUpdateException();
        Optional<Client> optionalClient = repository.findById(id);
        if (optionalClient.isEmpty()) return ResponseEntity.notFound().build();
        Client updatedClient = optionalClient.get();
        if (data.name() != null) updatedClient.setName(data.name());
        if (data.phone() != null) updatedClient.setPhone(data.phone());
        if (data.cpf() != null) updatedClient.setCpf(data.cpf());
        if (data.birthdate() != null) {
            updatedClient.setBirthdate(data.birthdate());
            updatedClient.setAge(Period.between(data.birthdate(), LocalDate.now()).getYears());
        }
        if (data.plan() != null) updatedClient.setPlan(data.plan());
        repository.save(updatedClient);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity toPay(Long id){
        Optional<Client> client = repository.findById(id);
        if(client.isEmpty()) return ResponseEntity.notFound().build();
        if(client.get().getDateEnd().isAfter(LocalDate.now())){
            client.get().setDateEnd(LocalDate.now().plusMonths(1));
            client.get().setDateStart(LocalDate.now());
        }else {
            client.get().setDateEnd(client.get().getDateEnd().plusMonths(1));
            client.get().setDateStart(LocalDate.now());
        }
        return ResponseEntity.ok().build();
    }

    public boolean verifyDataClientToUpdate(DataClient dataClient){
        return dataClient.name() == null && dataClient.phone() == null && dataClient.birthdate() == null && dataClient.cpf() == null;
    }

}
