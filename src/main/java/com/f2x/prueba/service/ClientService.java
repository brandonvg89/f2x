package com.f2x.prueba.service;

import com.f2x.prueba.dto.ClientDto;

public interface ClientService {
    ClientDto createClient(ClientDto clientDto);
    ClientDto updateClient(ClientDto clientDto);
    void deleteClient(ClientDto clientDto);
}
