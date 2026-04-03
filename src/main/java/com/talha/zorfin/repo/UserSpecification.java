package com.talha.zorfin.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.talha.zorfin.dto.UserSearchRequest;
import com.talha.zorfin.entity.User;

import jakarta.persistence.criteria.Predicate;

public class UserSpecification {
    
    public static Specification<User> getFilteredUsers(UserSearchRequest request) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.name() != null && !request.name().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.name() + "%"));                
            }
            if (request.email() != null && !request.email().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.email() + "%"));                
            }
            if (request.role() != null) {
                predicates.add(criteriaBuilder.equal(root.get("role"), request.role()));
            }
            if (request.status() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), request.status()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
