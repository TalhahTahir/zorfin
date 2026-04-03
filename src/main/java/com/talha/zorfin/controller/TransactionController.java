package com.talha.zorfin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talha.zorfin.dto.TransactionDto;
import com.talha.zorfin.dto.TransactionSearchRequest;
import com.talha.zorfin.service.TransactionService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;

    @PostMapping
    public TransactionDto createTransaction(@RequestBody TransactionDto dto) {
        return transactionService.createTransaction(dto);
    }

    @GetMapping("/{id}")
    public TransactionDto gettransaction(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping
    public List<TransactionDto> getTransactions(TransactionSearchRequest request) {
        return transactionService.getTransactions(request);
    }

    @PutMapping("/{id}")
    public TransactionDto updateTransaction(@PathVariable Long id, @RequestBody TransactionDto dto) {
        return transactionService.updateTransaction(id, dto);
    }
    
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
    
}