package com.f2x.prueba.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto {
    private Integer id;
    private ProductDto fromProduct;
    private ProductDto toProduct;
    private Double balance;
}
