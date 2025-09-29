package com.f2x.prueba.service.impl;

import com.f2x.prueba.dto.ProductDto;
import com.f2x.prueba.dto.TransactionDto;
import com.f2x.prueba.model.Product;
import com.f2x.prueba.model.Transaction;
import com.f2x.prueba.model.enums.OperationType;
import com.f2x.prueba.repository.TransactionRepository;
import com.f2x.prueba.service.ProductService;
import com.f2x.prueba.service.TransactionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ProductService productService;

    public TransactionServiceImpl(TransactionRepository transactionRepository, ProductService productService) {
        this.transactionRepository = transactionRepository;
        this.productService = productService;
    }

    @Override
    public boolean process(TransactionDto transactionDto) {
        ProductDto fromProductDto = productService.findProductById(transactionDto.getFromProduct().getId());
        ProductDto toProductDto = productService.findProductById(transactionDto.getToProduct().getId());
        if (fromProductDto.getBalance() > 0 && fromProductDto.getBalance() >= transactionDto.getBalance()) {
            boolean fromProductUpdated = productService.updateBalance(fromProductDto, OperationType.SUBTRACTION,
                    transactionDto.getBalance());
            boolean toProductUpdated = productService.updateBalance(toProductDto, OperationType.ADDICTION,
                    transactionDto.getBalance());
            Transaction transaction = new Transaction();
            BeanUtils.copyProperties(transactionDto, transaction);
            Product fromProduct = new Product();
            Product toProduct = new Product();
            BeanUtils.copyProperties(transactionDto.getFromProduct(), fromProduct);
            BeanUtils.copyProperties(transactionDto.getToProduct(), toProduct);
            transaction.setFromProduct(fromProduct);
            transaction.setToProduct(toProduct);
            transactionRepository.save(transaction);
            return fromProductUpdated && toProductUpdated;
        }
        return false;
    }
}
