package com.f2x.prueba.dto;

import com.f2x.prueba.model.enums.ProductStatus;
import com.f2x.prueba.model.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Integer id;
    private ProductType productType;
    private String productNumber;
    private ProductStatus productStatus;
    private Double balance;
    private boolean exemptGMF;
    private Date dateCreated;
    private Date dateModified;
    private ClientDto client;
}
