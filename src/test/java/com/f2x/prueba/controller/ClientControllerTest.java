package com.f2x.prueba.controller;

import com.f2x.prueba.dto.ClientDto;
import com.f2x.prueba.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    //@Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void createClient_ShouldReturnOk() throws Exception {
        // Arrange usando el Builder
        ClientDto clientDto = ClientDto.builder()
                .name("Juan")
                .lastname("Perez")
                .mail("juan.perez@example.com")
                .build();
        when(clientService.createClient(Mockito.any(ClientDto.class))).thenReturn(clientDto);

        // Act & Assert
        mockMvc.perform(post("/client")
                        .contentType("application/json")
                        .content("{ \"name\": \"Juan\", \"lastname\": \"Perez\", \"mail\": \"juan.perez@example.com\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("Juan")))
                .andExpect(jsonPath("$.lastname", is("Perez")))
                .andExpect(jsonPath("$.mail", is("juan.perez@example.com")));
    }

    @Test
    void updateClient_ShouldReturnOk() throws Exception {
        // Arrange usando el Builder
        ClientDto clientDto = ClientDto.builder()
                .name("Juan")
                .lastname("Perez")
                .mail("juan.perez@example.com")
                .build();
        when(clientService.updateClient(Mockito.any(ClientDto.class))).thenReturn(clientDto);

        // Act & Assert
        mockMvc.perform(post("/client/update")
                        .contentType("application/json")
                        .content("{ \"name\": \"Juan\", \"lastname\": \"Perez\", \"mail\": \"juan.perez@example.com\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("Juan")))
                .andExpect(jsonPath("$.lastname", is("Perez")))
                .andExpect(jsonPath("$.mail", is("juan.perez@example.com")));
    }

    @Test
    void deleteClient_ShouldReturnOk_WhenDeletedSuccessfully() throws Exception {
        // Arrange usando el Builder
        ClientDto clientDto = ClientDto.builder()
                .name("Juan")
                .lastname("Perez")
                .mail("juan.perez@example.com")
                .build();
        when(clientService.deleteClient(Mockito.any(ClientDto.class))).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/client/delete")
                        .contentType("application/json")
                        .content("{ \"name\": \"Juan\", \"lastname\": \"Perez\", \"mail\": \"juan.perez@example.com\" }"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteClient_ShouldReturnInternalServerError_WhenDeleteFails() throws Exception {
        // Arrange usando el Builder
        ClientDto clientDto = ClientDto.builder()
                .name("Juan")
                .lastname("Perez")
                .mail("juan.perez@example.com")
                .build();
        when(clientService.deleteClient(Mockito.any(ClientDto.class))).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/client/delete")
                        .contentType("application/json")
                        .content("{ \"name\": \"Juan\", \"lastname\": \"Perez\", \"mail\": \"juan.perez@example.com\" }"))
                .andExpect(status().isInternalServerError());
    }
}
