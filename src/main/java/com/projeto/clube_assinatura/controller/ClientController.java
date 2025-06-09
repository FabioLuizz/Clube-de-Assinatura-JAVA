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

    @PutMapping("/{id}")
    public ResponseEntity<Client> UpdateClient(@PathVariable Long id, @RequestBody Client updateClient) {

        try {
            Client update = clientService.updateClient(id, updateClient);
            return ResponseEntity.ok(update);
        } catch (RuntimeException e) {
            return ResponseEntity.ok().build();
        }

    }

}
