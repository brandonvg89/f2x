package com.f2x.prueba.service.impl;

import com.f2x.prueba.dto.ProductDto;
import com.f2x.prueba.model.Client;
import com.f2x.prueba.model.Product;
import com.f2x.prueba.model.enums.OperationType;
import com.f2x.prueba.model.enums.ProductStatus;
import com.f2x.prueba.model.enums.ProductType;
import com.f2x.prueba.repository.ProductRepository;
import com.f2x.prueba.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String AHORRO_PREFIX = "53";
    private static final String CORRIENTE_PREFIX = "53";
    private static final SecureRandom random = new SecureRandom();
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        productDto.setProductNumber(generateCard(productDto.getProductType().name()));
        productDto.setProductStatus(ProductStatus.ACTIVA);
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        product.setBalance((double) 0);
        product.setDateCreated(new Date());
        Client client = new Client();
        BeanUtils.copyProperties(productDto.getClient(), client);
        product.setClient(client);
        return productRepository.save(product).convertToDto();
    }

    @Override
    public ProductDto changeStatusProduct(ProductDto productDto) throws Exception {
        switch (productDto.getProductStatus()) {
            case ProductStatus.ACTIVA, ProductStatus.INACTIVA -> {
                Product product = productRepository.findById(productDto.getId()).orElseThrow();
                BeanUtils.copyProperties(productDto, product);
                product.setDateModified(new Date());
                Client client = new Client();
                BeanUtils.copyProperties(productDto.getClient(), client);
                product.setClient(client);
                return productRepository.save(product).convertToDto();
            }
            case ProductStatus.CANCELADA -> {
                Product product = productRepository.findById(productDto.getId()).orElseThrow();
                product.setDateModified(new Date());
                Client client = new Client();
                BeanUtils.copyProperties(productDto.getClient(), client);
                product.setClient(client);
                if (product.getBalance() == 0) {
                    product.setProductStatus(ProductStatus.CANCELADA);
                    return productRepository.save(product).convertToDto();
                }
            }
            default -> throw new Exception("Operation doesn´t support");
        }
        throw new Exception("Operation doesn´t support");
    }

    @Override
    public ProductDto findProductById(Integer id) {
        return productRepository.findById(id).orElseThrow().convertToDto();
    }

    @Override
    public boolean updateBalance(ProductDto productDto, OperationType operationType, Double balance) {
        try {
            Product product = productRepository.findById(productDto.getId()).orElseThrow();
            product.setDateModified(new Date());
            switch (operationType) {
                case OperationType.ADDICTION ->
                        product.setBalance(product.getBalance() + balance);
                case OperationType.SUBTRACTION ->
                        product.setBalance(product.getBalance() - balance);
                default -> throw new Exception("Operation not allowed");
            }
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean findProductByClientId(Integer id) {
        return productRepository.findByClientId(id).isPresent();
    }

    public String generateCard(String type) {
        String prefix = type.equalsIgnoreCase(ProductType.AHORRO.name()) ? AHORRO_PREFIX : CORRIENTE_PREFIX;
        int num = random.nextInt(100_000_000);
        String body = String.format("%08d", num);
        return prefix + body;
    }
}
