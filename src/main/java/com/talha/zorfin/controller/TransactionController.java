package com.talha.zorfin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.talha.zorfin.dto.TransactionDto;
import com.talha.zorfin.dto.TransactionSearchRequest;
import com.talha.zorfin.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@PreAuthorize("hasAnyRole('ANALYST', 'ADMIN')")
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can create transactions
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto createTransaction(@Valid @RequestBody TransactionDto dto) {
        return transactionService.createTransaction(dto);
    }

    @GetMapping("/{id}")
    public TransactionDto gettransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping
    public List<TransactionDto> getTransactions(TransactionSearchRequest request) {
        return transactionService.getTransactions(request);
    }

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can update transactions
    @PutMapping("/{id}")
    public TransactionDto updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionDto dto) {
        return transactionService.updateTransaction(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can delete transactions
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

}