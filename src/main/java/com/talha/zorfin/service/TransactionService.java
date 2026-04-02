package com.talha.zorfin.service;

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

    List<TransactionDto> getTransactionsByType(TransactionType type);

    List<TransactionDto> getTransactionsByCategory(TransactionCategory category);

    List<TransactionDto> getTransactionsByTypeAndCategory(TransactionType type, TransactionCategory category);

    List<TransactionDto> getTransactionsByDateRange(String startDate, String endDate);

    List<TransactionDto> getTransactionsByTypeAndDateRange(TransactionType type, String startDate, String endDate);

    List<TransactionDto> getTransactionsByCategoryAndDateRange(TransactionCategory category, String startDate,
            String endDate);

}
