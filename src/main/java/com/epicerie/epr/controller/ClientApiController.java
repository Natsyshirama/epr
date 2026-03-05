package com.epicerie.epr.controller;

import com.epicerie.epr.model.Client;
import com.epicerie.epr.repository.ClientRepository;

import org.springframework.lang.NonNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "http://localhost:5173")
public class ClientApiController {

    private final ClientRepository clientRepository;

    public ClientApiController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    // GET ALL
    @GetMapping
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Client getClientById(@PathVariable @NonNull Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Client introuvable : " + id));
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client createClient(@RequestBody @NonNull Client client) {
        return clientRepository.save(client);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Client updateClient(@PathVariable @NonNull Long id, @RequestBody Client client) {
        if (!clientRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Client introuvable : " + id);
        }
        client.setId(id);
        return clientRepository.save(client);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable @NonNull Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Client introuvable : " + id);
        }
        clientRepository.deleteById(id);
    }
}