package com.talha.zorfin.service;

import java.util.List;

import com.talha.zorfin.dto.PagedResponse;
import com.talha.zorfin.dto.TransactionDto;
import com.talha.zorfin.dto.TransactionSearchRequest;

public interface TransactionService {

    TransactionDto createTransaction(TransactionDto transactionDto);

    TransactionDto getTransactionById(Long id);

    TransactionDto updateTransaction(Long id, TransactionDto transactionDto);

    void deleteTransaction(Long id);

    PagedResponse<TransactionDto> getTransactions(
        TransactionSearchRequest request,
        int page,
        int size,
        String sortBy,
        String sortDir);

}
