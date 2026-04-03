package com.talha.zorfin.service;

import java.time.Instant;
import java.util.List;

import com.talha.zorfin.dto.TransactionDto;
import com.talha.zorfin.enums.TransactionCategory;
import com.talha.zorfin.enums.TransactionType;

public interface TransactionService {

    TransactionDto createTransaction(TransactionDto transactionDto);

    TransactionDto getTransactionById(Long id);

    List<TransactionDto> getAllTransactions();

    TransactionDto updateTransaction(Long id, TransactionDto transactionDto);

    void deleteTransaction(Long id);

    List<TransactionDto> getTransactions(
        TransactionType type, TransactionCategory category, Instant startDate, Instant endDate);

}
