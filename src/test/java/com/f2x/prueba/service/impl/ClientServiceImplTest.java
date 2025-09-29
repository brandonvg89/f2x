package com.f2x.prueba.service.impl;

import com.f2x.prueba.dto.ClientDto;
import com.f2x.prueba.model.Client;
import com.f2x.prueba.repository.ClientRepository;
import com.f2x.prueba.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Client getMockClient(Integer id) {
        Client client = new Client();
        client.setId(id);
        client.setName("John Doe");
        client.setDateCreated(new Date());
        return client;
    }

    private ClientDto getMockClientDto(Integer id) {
        ClientDto dto = new ClientDto();
        dto.setId(id);
        dto.setName("John Doe");
        return dto;
    }

    @Test
    void testCreateClient() {
        // Arrange
        ClientDto clientDto = new ClientDto();
        clientDto.setName("John Doe");
        Client savedClient = getMockClient(1);

        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        // Act
        ClientDto result = clientService.createClient(clientDto);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void testUpdateClient() {
        // Arrange
        ClientDto clientDto = getMockClientDto(1);
        Client existingClient = getMockClient(1);

        when(clientRepository.findById(1)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(Client.class))).thenReturn(existingClient);

        // Act
        ClientDto result = clientService.updateClient(clientDto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(clientRepository, times(1)).findById(1);
        verify(clientRepository, times(1)).save(existingClient);
    }

    @Test
    void testDeleteClient_WhenNoProducts() {
        // Arrange
        ClientDto clientDto = getMockClientDto(1);
        Client existingClient = getMockClient(1);

        when(clientRepository.findById(1)).thenReturn(Optional.of(existingClient));
        when(productService.findProductByClientId(1)).thenReturn(false);

        // Act
        boolean result = clientService.deleteClient(clientDto);

        // Assert
        assertTrue(result);
        verify(clientRepository, times(1)).delete(existingClient);
    }

    @Test
    void testDeleteClient_WhenHasProducts() {
        // Arrange
        ClientDto clientDto = getMockClientDto(1);
        Client existingClient = getMockClient(1);

        when(clientRepository.findById(1)).thenReturn(Optional.of(existingClient));
        when(productService.findProductByClientId(1)).thenReturn(true);

        // Act
        boolean result = clientService.deleteClient(clientDto);

        // Assert
        assertFalse(result);
        verify(clientRepository, never()).delete(any(Client.class));
    }

    @Test
    void testUpdateClient_NotFound() {
        // Arrange
        ClientDto clientDto = getMockClientDto(99);
        when(clientRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> clientService.updateClient(clientDto));
    }

    @Test
    void testDeleteClient_NotFound() {
        // Arrange
        ClientDto clientDto = getMockClientDto(99);
        when(clientRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> clientService.deleteClient(clientDto));
    }
}