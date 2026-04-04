package com.talha.zorfin.service;

import java.util.List;

import com.talha.zorfin.dto.TransactionDto;
import com.talha.zorfin.dto.TransactionSearchRequest;

public interface TransactionService {

    TransactionDto createTransaction(TransactionDto transactionDto);

    TransactionDto getTransactionById(Long id);

    TransactionDto updateTransaction(Long id, TransactionDto transactionDto);

    void deleteTransaction(Long id);

    List<TransactionDto> getTransactions(TransactionSearchRequest request);

}
