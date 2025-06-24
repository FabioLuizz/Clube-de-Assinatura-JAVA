package com.projeto.clube_assinatura.service;

import com.projeto.clube_assinatura.dto.ClientDTO;
import com.projeto.clube_assinatura.model.Client;
import com.projeto.clube_assinatura.repository.ClientRepository;
import jakarta.validation.Valid;
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

    public Client saveClient(@Valid ClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setTel(clientDTO.getTel());

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

    public Client updateClient(Long id, @Valid ClientDTO updateClientDTO) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setName(updateClientDTO.getName());
                    client.setEmail(updateClientDTO.getEmail());
                    client.setCpf(updateClientDTO.getCpf());
                    client.setTel(updateClientDTO.getTel());
                    return clientRepository.save(client);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado!"));
    }

}
