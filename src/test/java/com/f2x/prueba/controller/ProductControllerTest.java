package com.f2x.prueba.controller;

import com.f2x.prueba.dto.ClientDto;
import com.f2x.prueba.dto.ProductDto;
import com.f2x.prueba.model.enums.ProductStatus;
import com.f2x.prueba.model.enums.ProductType;
import com.f2x.prueba.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void createProduct_ShouldReturnOk() throws Exception {
        ProductDto productDto = ProductDto.builder()
                .id(1)
                .productType(ProductType.AHORRO)
                .productStatus(ProductStatus.ACTIVA)
                .exemptGMF(true)
                .dateCreated(new Date())
                .dateModified(new Date())
                .client(ClientDto.builder()
                        .id(1)
                        .name("Juan")
                        .lastname("Perez")
                        .mail("juan.perez@example.com")
                        .build())
                .build();

        when(productService.createProduct(Mockito.any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(post("/product")
                        .contentType("application/json")
                        .content("{ " +
                                "\"id\": 1, " +
                                "\"productType\": \"AHORRO\", " +
                                "\"productStatus\": \"ACTIVA\", " +
                                "\"exemptGMF\": true, " +
                                "\"dateCreated\": \"2023-09-29T12:00:00\", " +
                                "\"dateModified\": \"2023-09-29T12:00:00\", " +
                                "\"client\": { " +
                                "\"name\": \"Juan\", " +
                                "\"lastname\": \"Perez\", " +
                                "\"mail\": \"juan.perez@example.com\" " +
                                "} " +
                                " }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.productType", is("AHORRO")))
                .andExpect(jsonPath("$.productStatus", is("ACTIVA")))
                .andExpect(jsonPath("$.exemptGMF", is(true)))
                .andExpect(jsonPath("$.client.name", is("Juan")))
                .andExpect(jsonPath("$.client.lastname", is("Perez")))
                .andExpect(jsonPath("$.client.mail", is("juan.perez@example.com")));
    }

    @Test
    void changeStatus_ShouldReturnOk() throws Exception {
        ProductDto productDto = ProductDto.builder()
                .productType(ProductType.AHORRO)
                .exemptGMF(true)
                .productStatus(ProductStatus.INACTIVA)
                .client(ClientDto.builder().id(1).build())
                .build();

        when(productService.changeStatusProduct(Mockito.any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(post("/product/status")
                        .contentType("application/json")
                        .content("{ " +
                                "\"id\": 1, " +
                                "\"productType\": \"AHORRO\", " +
                                "\"productStatus\": \"INACTIVA\", " +
                                "\"exemptGMF\": true, " +
                                "\"dateCreated\": \"2023-09-29T12:00:00\", " +
                                "\"dateModified\": \"2023-09-29T12:00:00\", " +
                                "\"client\": { " +
                                "\"name\": \"Juan\", " +
                                "\"lastname\": \"Perez\", " +
                                "\"mail\": \"juan.perez@example.com\" " +
                                "} " +
                                " }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.productStatus", is("INACTIVA")));
    }
}
