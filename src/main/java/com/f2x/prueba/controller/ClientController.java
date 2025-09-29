package com.f2x.prueba.controller;

import com.f2x.prueba.dto.ClientDto;
import com.f2x.prueba.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    private ResponseEntity<ClientDto> createClient(@Valid @RequestBody ClientDto clientDto) {
        return new ResponseEntity<>(clientService.createClient(clientDto), HttpStatus.OK);
    }

    @PostMapping("/update")
    private ResponseEntity<ClientDto> updateClient(@Valid @RequestBody ClientDto clientDto) {
        return new ResponseEntity<>(clientService.updateClient(clientDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    private ResponseEntity<ClientDto> deleteClient(@Valid @RequestBody ClientDto clientDto) {
        if(clientService.deleteClient(clientDto)){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
