package com.talha.zorfin.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.talha.zorfin.entity.User;
import com.talha.zorfin.enums.UserRole;
import com.talha.zorfin.enums.UserStatus;

import jakarta.persistence.criteria.Predicate;

public class UserSpecification {
    
    public static Specification<User> getFilteredUsers(
            String name,
            String email,
            UserRole role,
            UserStatus status) {

        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));                
            }
            if (email != null && !email.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + email + "%"));                
            }
            if (role != null) {
                predicates.add(criteriaBuilder.equal(root.get("role"), role));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
