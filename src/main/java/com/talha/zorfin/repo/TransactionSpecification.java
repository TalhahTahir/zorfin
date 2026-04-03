package com.talha.zorfin.repo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.talha.zorfin.entity.Transaction;
import com.talha.zorfin.enums.TransactionCategory;
import com.talha.zorfin.enums.TransactionType;

import jakarta.persistence.criteria.Predicate;

public class TransactionSpecification {

    public static Specification<Transaction> getFilteredTransactions(
            TransactionType type, TransactionCategory category, Instant startDate, Instant endDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            if (category != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate));
            }
            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
