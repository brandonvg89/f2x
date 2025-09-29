package com.f2x.prueba.service.impl;

import com.f2x.prueba.dto.ProductDto;
import com.f2x.prueba.dto.TransactionDto;
import com.f2x.prueba.model.Product;
import com.f2x.prueba.model.Transaction;
import com.f2x.prueba.model.enums.OperationType;
import com.f2x.prueba.repository.TransactionRepository;
import com.f2x.prueba.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private ProductDto fromProductDto;
    private ProductDto toProductDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        fromProductDto = new ProductDto();
        fromProductDto.setId(1);
        fromProductDto.setBalance(200.0);

        toProductDto = new ProductDto();
        toProductDto.setId(2);
        toProductDto.setBalance(100.0);
    }

    private TransactionDto getTransactionDto(double amount) {
        TransactionDto dto = new TransactionDto();
        dto.setBalance(amount);
        dto.setFromProduct(fromProductDto);
        dto.setToProduct(toProductDto);
        return dto;
    }

    @Test
    void testProcess_SuccessfulTransaction() {
        TransactionDto dto = getTransactionDto(100.0);

        when(productService.findProductById(1)).thenReturn(fromProductDto);
        when(productService.findProductById(2)).thenReturn(toProductDto);
        when(productService.updateBalance(fromProductDto, OperationType.SUBTRACTION, 100.0)).thenReturn(true);
        when(productService.updateBalance(toProductDto, OperationType.ADDICTION, 100.0)).thenReturn(true);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        boolean result = transactionService.process(dto);

        assertTrue(result);
        verify(productService).updateBalance(fromProductDto, OperationType.SUBTRACTION, 100.0);
        verify(productService).updateBalance(toProductDto, OperationType.ADDICTION, 100.0);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testProcess_InsufficientFunds() {
        fromProductDto.setBalance(50.0); // menos que lo que se quiere transferir
        TransactionDto dto = getTransactionDto(100.0);

        when(productService.findProductById(1)).thenReturn(fromProductDto);
        when(productService.findProductById(2)).thenReturn(toProductDto);

        boolean result = transactionService.process(dto);

        assertFalse(result);
        verify(productService, never()).updateBalance(any(), any(), anyDouble());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void testProcess_UpdateBalanceFails() {
        TransactionDto dto = getTransactionDto(100.0);

        when(productService.findProductById(1)).thenReturn(fromProductDto);
        when(productService.findProductById(2)).thenReturn(toProductDto);
        when(productService.updateBalance(fromProductDto, OperationType.SUBTRACTION, 100.0)).thenReturn(true);
        when(productService.updateBalance(toProductDto, OperationType.ADDICTION, 100.0)).thenReturn(false);

        boolean result = transactionService.process(dto);

        assertFalse(result);
        verify(transactionRepository, times(1)).save(any(Transaction.class)); // igual guarda la transacción
    }
}
