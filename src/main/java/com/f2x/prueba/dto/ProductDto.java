package com.f2x.prueba.dto;

import com.f2x.prueba.model.enums.ProductStatus;
import com.f2x.prueba.model.enums.ProductType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
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
