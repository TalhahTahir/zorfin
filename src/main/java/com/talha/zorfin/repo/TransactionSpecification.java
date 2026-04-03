package com.talha.zorfin.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.talha.zorfin.dto.TransactionSearchRequest;
import com.talha.zorfin.entity.Transaction;

import jakarta.persistence.criteria.Predicate;

public class TransactionSpecification {

    public static Specification<Transaction> getFilteredTransactions(TransactionSearchRequest request) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.title() != null && !request.title().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + request.title() + "%"));
            }
            if (request.type() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), request.type()));
            }
            if (request.category() != null) {
                predicates.add(criteriaBuilder.equal(root.get("category"), request.category()));
            }
            if (request.startDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), request.startDate()));
            }
            if (request.endDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), request.endDate()));
            }
            if (request.minAmount() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), request.minAmount()));
            }
            if (request.maxAmount() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), request.maxAmount()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
