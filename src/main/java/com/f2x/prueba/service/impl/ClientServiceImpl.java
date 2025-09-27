package com.f2x.prueba.service.impl;

import com.f2x.prueba.dto.ClientDto;
import com.f2x.prueba.model.Client;
import com.f2x.prueba.repository.ClientRepository;
import com.f2x.prueba.service.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDto, client);
        return clientRepository.save(client).convertToDto();
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDto, client);
        return clientRepository.save(client).convertToDto();
    }

    @Override
    public void deleteClient(ClientDto clientDto) {
        //TODO: To delete client we need validate if the client doesn´t have products associated
    }
}
