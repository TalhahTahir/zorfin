package com.talha.zorfin.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talha.zorfin.entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    
}
