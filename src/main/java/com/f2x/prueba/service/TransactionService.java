package com.f2x.prueba.service;

import com.f2x.prueba.dto.TransactionDto;

public interface TransactionService {
    boolean process(TransactionDto transactionDto);
}
