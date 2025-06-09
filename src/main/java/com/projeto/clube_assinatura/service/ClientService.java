package com.projeto.clube_assinatura.service;

import com.projeto.clube_assinatura.model.Client;
import com.projeto.clube_assinatura.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    public ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public Client updateClient(Long id, Client updateClient) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setName(updateClient.getName());
                    client.setEmail(updateClient.getEmail());
                    client.setCpf(updateClient.getCpf());
                    client.setTel(updateClient.getTel());
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    }

}
