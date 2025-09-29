package com.f2x.prueba.model;

import com.f2x.prueba.dto.ProductDto;
import com.f2x.prueba.model.enums.ProductStatus;
import com.f2x.prueba.model.enums.ProductType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Table
@Entity(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "product_number", unique = true)
    private String productNumber;

    @Column(name = "status")
    private ProductStatus productStatus;

    @Column(name = "balance")
    @ColumnDefault("0")
    private Double balance;

    @Column(name = "exempt_gmf")
    private boolean exemptGMF;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "date_modified")
    private Date dateModified;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public ProductDto convertToDto() {
        return ProductDto.builder()
                .id(id)
                .productType(productType)
                .productNumber(productNumber)
                .productStatus(productStatus)
                .balance(balance)
                .exemptGMF(exemptGMF)
                .dateCreated(dateCreated)
                .dateModified(dateModified)
                .client(client.convertToDto())
                .build();
    }
}
