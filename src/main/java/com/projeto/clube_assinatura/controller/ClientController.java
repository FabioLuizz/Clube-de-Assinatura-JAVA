package com.projeto.clube_assinatura.controller;

import com.projeto.clube_assinatura.model.Client;
import com.projeto.clube_assinatura.repository.ClientRepository;
import com.projeto.clube_assinatura.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    public ClientService clientService;

    public ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> CreateClient(@RequestBody Client client) {
        Client saveClient = clientService.saveClient(client);
        return ResponseEntity.ok(saveClient);
    }

    @GetMapping
    public ResponseEntity<List<Client>> GetAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> GetById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> UpdateClient(@PathVariable Long id, @RequestBody Client updateClient) {

        return clientRepository.findById(id)
                .map(client -> {
                    client.setName(updateClient.getName());
                    client.setEmail(updateClient.getEmail());
                    client.setCpf(updateClient.getCpf());
                    client.setTel(updateClient.getTel());
                    Client savedClient = clientRepository.save(client);
                    return ResponseEntity.ok(client);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
