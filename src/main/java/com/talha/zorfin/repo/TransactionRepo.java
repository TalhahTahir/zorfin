package com.talha.zorfin.repo;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.talha.zorfin.dto.CategoryStatsDto;
import com.talha.zorfin.dto.TypeStatsDto;
import com.talha.zorfin.entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    @Query("""
            SELECT new com.talha.zorfin.dto.CategoryStatsDto(
            t.category, COUNT(t), SUM(t.amount), AVG(t.amount), MIN(t.amount), MAX(t.amount)
            )
            FROM Transaction t
            WHERE t.createdAt >= :startDate AND t.createdAt <= :endDate
            GROUP BY t.category
            """)
    List<CategoryStatsDto> getCategoryStats(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);


    @Query("""
            SELECT new com.talha.zorfin.dto.TypeStatsDto(
            t.type, COUNT(t), SUM(t.amount), AVG(t.amount), MIN(t.amount), MAX(t.amount)
            )
            FROM Transaction t
            WHERE t.createdAt >= :startDate AND t.createdAt <= :endDate
            GROUP BY t.type
            """)
    List<TypeStatsDto> getTypeStats(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
}
