package com.talha.zorfin.service.serviceImpl;

import java.time.Instant;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.talha.zorfin.dto.PagedResponse;
import com.talha.zorfin.dto.TransactionDto;
import com.talha.zorfin.dto.TransactionSearchRequest;
import com.talha.zorfin.entity.Transaction;
import com.talha.zorfin.exception.ResourceNotFoundException;
import com.talha.zorfin.repo.TransactionRepo;
import com.talha.zorfin.repo.TransactionSpecification;
import com.talha.zorfin.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final ModelMapper mapper;

    @Override
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction tx = mapper.map(transactionDto, Transaction.class);
        tx.setCreatedAt(Instant.now());
        Transaction savedTx = transactionRepo.save(tx);
        return mapper.map(savedTx, TransactionDto.class);
    }

    @Override
    public TransactionDto getTransactionById(Long id) {
        Transaction tx = transactionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        return mapper.map(tx, TransactionDto.class);
    }

    @Override
    public PagedResponse<TransactionDto> getTransactions(TransactionSearchRequest request, int page, int size, String sortBy, String sortDir) {

        // Determining the sort direction
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) 
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

Specification<Transaction> spec = TransactionSpecification.getFilteredTransactions(request);
        Page<Transaction> transactionPage = transactionRepo.findAll(spec, pageable);

        List<TransactionDto> content = transactionPage.getContent().stream()
                .map(t -> mapper.map(t, TransactionDto.class))
                .toList();

        return new PagedResponse<>(
                content,
                transactionPage.getNumber(),
                transactionPage.getSize(),
                transactionPage.getTotalElements(),
                transactionPage.getTotalPages(),
                transactionPage.isLast()
        );
    }

    @Override
    public TransactionDto updateTransaction(Long id, TransactionDto transactionDto) {
        Transaction tx = transactionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        mapper.map(transactionDto, tx);
        tx.setUpdatedAt(Instant.now());
        Transaction updatedTx = transactionRepo.save(tx);
        return mapper.map(updatedTx, TransactionDto.class);
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction tx = transactionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        transactionRepo.delete(tx);
    }

}
