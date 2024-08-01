package com.example.gym_system.domain.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
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


    public ResponseEntity getAllClients() {
        List<DataListingClient> listOfClients = repository.findAll().stream()
                .map(client -> new DataListingClient(client.getId(), client.getName(), client.getAge(), client.getPhone(), client.getPlan())).toList();
        return ResponseEntity.ok(listOfClients);
    }

    public ResponseEntity deleteAClientById(Long id){
        if(!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity getAClientById(Long id){
        if(!repository.existsById(id)) return ResponseEntity.notFound().build();
        var dataClient = repository.findById(id).get();
        return ResponseEntity.ok(new DataClient(dataClient.getId(), dataClient.getName(), dataClient.getPhone(), dataClient.getCpf(), dataClient.getBirthdate(), dataClient.getPlan()));
    }

    public ResponseEntity updateAnExistingClient(DataClient data){
        Optional<Client> optionalClient = repository.findById(data.id());
        if(optionalClient.isEmpty()) return ResponseEntity.notFound().build();
        Client updatedClient = optionalClient.get();
        if (data.name() != null) updatedClient.setName(data.name());
        if (data.phone() != null) updatedClient.setPhone(data.phone());
        if (data.cpf() != null) updatedClient.setCpf(data.cpf());
        if (data.birthdate() != null) updatedClient.setBirthdate(data.birthdate());
        if (data.plan() != null) updatedClient.setPlan(data.plan());
        repository.save(updatedClient);
        return ResponseEntity.ok().build();
    }

}
