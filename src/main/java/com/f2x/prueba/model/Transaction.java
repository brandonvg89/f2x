package com.f2x.prueba.model;

import com.f2x.prueba.dto.TransactionDto;
import jakarta.persistence.*;
import lombok.Data;

@Table
@Entity(name = "transaction")
@Data
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "from_product")
    private Product fromProduct;

    @ManyToOne
    @JoinColumn(name = "to_product")
    private Product toProduct;

    @Column(name = "balance")
    private Double balance;

    public TransactionDto convertToDto() {
        return TransactionDto.builder()
                .id(id)
                .fromProduct(fromProduct.convertToDto())
                .toProduct(toProduct.convertToDto())
                .balance(balance)
                .build();
    }
}
