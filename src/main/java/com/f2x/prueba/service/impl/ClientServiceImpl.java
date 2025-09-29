package com.f2x.prueba.service.impl;

import com.f2x.prueba.dto.ClientDto;
import com.f2x.prueba.model.Client;
import com.f2x.prueba.repository.ClientRepository;
import com.f2x.prueba.service.ClientService;
import com.f2x.prueba.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ProductService productService;

    public ClientServiceImpl(ClientRepository clientRepository, ProductService productService) {
        this.clientRepository = clientRepository;
        this.productService = productService;
    }

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDto, client);
        client.setId(null);
        client.setDateCreated(new Date());
        return clientRepository.save(client).convertToDto();
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto) {
        Client client = clientRepository.findById(clientDto.getId()).orElseThrow();
        BeanUtils.copyProperties(clientDto, client);
        client.setDateModified(new Date());
        return clientRepository.save(client).convertToDto();
    }

    @Override
    public boolean deleteClient(ClientDto clientDto) {
        Client client = clientRepository.findById(clientDto.getId()).orElseThrow();
        boolean clientHasProducts = productService.findProductByClientId(client.getId());
        if(!clientHasProducts) {
            clientRepository.delete(client);
            return true;
        }
        return false;
    }
}
