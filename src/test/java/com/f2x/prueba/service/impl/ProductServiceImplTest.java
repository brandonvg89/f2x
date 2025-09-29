package com.f2x.prueba.service.impl;

import com.f2x.prueba.dto.ClientDto;
import com.f2x.prueba.dto.ProductDto;
import com.f2x.prueba.model.Client;
import com.f2x.prueba.model.Product;
import com.f2x.prueba.model.enums.OperationType;
import com.f2x.prueba.model.enums.ProductStatus;
import com.f2x.prueba.model.enums.ProductType;
import com.f2x.prueba.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Product getMockProduct(Integer id) {
        Product product = new Product();
        product.setId(id);
        product.setBalance(100.0);
        product.setProductStatus(ProductStatus.ACTIVA);
        product.setProductType(ProductType.AHORRO);
        product.setProductNumber("5300000001");
        product.setClient(Client.builder().id(1).build());
        return product;
    }

    private ProductDto getMockProductDto(Integer id) {
        ProductDto dto = new ProductDto();
        dto.setId(id);
        dto.setBalance(100.0);
        dto.setProductStatus(ProductStatus.ACTIVA);
        dto.setProductType(ProductType.AHORRO);
        dto.setClient(ClientDto.builder().id(1).build());
        return dto;
    }

    @Test
    void testCreateProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setBalance(100.0);
        productDto.setProductStatus(ProductStatus.ACTIVA);
        productDto.setProductType(ProductType.AHORRO);
        productDto.setClient(ClientDto.builder().id(1).build());
        Product savedProduct = getMockProduct(1);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductDto result = productService.createProduct(productDto);

        assertNotNull(result);
        assertEquals(ProductStatus.ACTIVA, result.getProductStatus());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testChangeStatusProduct_Activa() throws Exception {
        ProductDto dto = getMockProductDto(1);
        dto.setProductStatus(ProductStatus.ACTIVA);

        Product existing = getMockProduct(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenReturn(existing);

        ProductDto result = productService.changeStatusProduct(dto);

        assertNotNull(result);
        assertEquals(ProductStatus.ACTIVA, result.getProductStatus());
    }

    @Test
    void testChangeStatusProduct_Inactiva() throws Exception {
        ProductDto dto = getMockProductDto(1);
        dto.setProductStatus(ProductStatus.INACTIVA);

        Product existing = getMockProduct(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenReturn(existing);

        ProductDto result = productService.changeStatusProduct(dto);

        assertNotNull(result);
        assertEquals(ProductStatus.INACTIVA, result.getProductStatus());
    }

    @Test
    void testChangeStatusProduct_CanceladaWithZeroBalance() throws Exception {
        ProductDto dto = getMockProductDto(1);
        dto.setProductStatus(ProductStatus.CANCELADA);

        Product existing = getMockProduct(1);
        existing.setBalance(0.0);

        when(productRepository.findById(1)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenReturn(existing);

        ProductDto result = productService.changeStatusProduct(dto);

        assertNotNull(result);
        assertEquals(ProductStatus.CANCELADA, result.getProductStatus());
    }

    @Test
    void testChangeStatusProduct_CanceladaWithBalanceNotZero() {
        ProductDto dto = getMockProductDto(1);
        dto.setProductStatus(ProductStatus.CANCELADA);

        Product existing = getMockProduct(1);
        existing.setBalance(50.0);

        when(productRepository.findById(1)).thenReturn(Optional.of(existing));

        assertThrows(Exception.class, () -> productService.changeStatusProduct(dto));
    }

    @Test
    void testChangeStatusProduct_InvalidStatus() {
        ProductDto dto = getMockProductDto(1);
        dto.setProductStatus(null);

        assertThrows(Exception.class, () -> productService.changeStatusProduct(dto));
    }

    @Test
    void testFindProductById() {
        Product product = getMockProduct(1);
        ProductDto dto = getMockProductDto(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        ProductDto result = productService.findProductById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    void testUpdateBalance_Addition() {
        ProductDto dto = getMockProductDto(1);
        Product product = getMockProduct(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        boolean result = productService.updateBalance(dto, OperationType.ADDICTION, 50.0);

        assertTrue(result);
        assertEquals(150.0, product.getBalance());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateBalance_Subtraction() {
        ProductDto dto = getMockProductDto(1);
        Product product = getMockProduct(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        boolean result = productService.updateBalance(dto, OperationType.SUBTRACTION, 30.0);

        assertTrue(result);
        assertEquals(70.0, product.getBalance());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateBalance_InvalidOperation() {
        ProductDto dto = getMockProductDto(1);
        Product product = getMockProduct(1);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        boolean result = productService.updateBalance(dto, null, 20.0);

        assertFalse(result);
    }

    @Test
    void testFindProductByClientId_WhenExists() {
        when(productRepository.findByClientId(1)).thenReturn(Optional.of(new Product()));

        boolean result = productService.findProductByClientId(1);

        assertTrue(result);
    }

    @Test
    void testFindProductByClientId_WhenNotExists() {
        when(productRepository.findByClientId(1)).thenReturn(Optional.empty());

        boolean result = productService.findProductByClientId(1);

        assertFalse(result);
    }

    @Test
    void testGenerateCard_Ahorro() {
        String card = productService.generateCard(ProductType.AHORRO.name());

        assertNotNull(card);
        assertTrue(card.startsWith("53"));
        assertEquals(10, card.length()); // "53" + 8 digits
    }

    @Test
    void testGenerateCard_Corriente() {
        String card = productService.generateCard(ProductType.CORRIENTE.name());

        assertNotNull(card);
        assertTrue(card.startsWith("53"));
        assertEquals(10, card.length());
    }
}
