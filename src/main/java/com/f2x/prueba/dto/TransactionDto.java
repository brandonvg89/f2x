package com.f2x.prueba.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Integer id;
    private ProductDto fromProduct;
    private ProductDto toProduct;
    private Double balance;
}
