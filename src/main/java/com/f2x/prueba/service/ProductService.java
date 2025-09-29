package com.f2x.prueba.service;

import com.f2x.prueba.dto.ProductDto;
import com.f2x.prueba.model.enums.OperationType;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto);
    ProductDto changeStatusProduct(ProductDto productDto) throws Exception;
    ProductDto findProductById(Integer id);
    boolean updateBalance(ProductDto productDto, OperationType operationType, Double balance);
    boolean findProductByClientId(Integer id);
}
