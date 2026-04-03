package com.talha.zorfin.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.talha.zorfin.dto.TransactionDto;
import com.talha.zorfin.dto.TransactionSearchRequest;
import com.talha.zorfin.enums.TransactionCategory;
import com.talha.zorfin.enums.TransactionType;

public interface TransactionService {

    TransactionDto createTransaction(TransactionDto transactionDto);

    TransactionDto getTransactionById(Long id);

    TransactionDto updateTransaction(Long id, TransactionDto transactionDto);

    void deleteTransaction(Long id);

    List<TransactionDto> getTransactions(TransactionSearchRequest request);

}
