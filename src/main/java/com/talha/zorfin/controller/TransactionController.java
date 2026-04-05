package com.talha.zorfin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.talha.zorfin.dto.PagedResponse;
import com.talha.zorfin.dto.TransactionDto;
import com.talha.zorfin.dto.TransactionSearchRequest;
import com.talha.zorfin.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


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
    @Operation(summary = "Create transaction", description = "Creates a new transaction entry.")
    public TransactionDto createTransaction(@Valid @RequestBody TransactionDto dto) {
        return transactionService.createTransaction(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID", description = "Fetches a transaction by its unique ID.")
    public TransactionDto gettransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping
    @Operation(summary = "List transactions", description = "Returns a paginated list of transactions with optional filters and sorting.")
    public PagedResponse<TransactionDto> getTransactions(
            TransactionSearchRequest request,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
        return transactionService.getTransactions(request, page, size, sortBy, sortDir);
    }

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can update transactions
    @PutMapping("/{id}")
    @Operation(summary = "Update transaction", description = "Updates an existing transaction by ID.")
    public TransactionDto updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionDto dto) {
        return transactionService.updateTransaction(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')") // Only ADMIN can delete transactions
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete transaction", description = "Deletes a transaction by ID.")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

}