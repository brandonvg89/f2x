package com.f2x.prueba.controller;

import com.f2x.prueba.dto.ProductDto;
import com.f2x.prueba.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    private ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.OK);
    }

    @PostMapping("/status")
    private ResponseEntity<ProductDto> changeStatus(@Valid @RequestBody ProductDto productDto) throws Exception {
        return new ResponseEntity<>(productService.changeStatusProduct(productDto), HttpStatus.OK);
    }
}
