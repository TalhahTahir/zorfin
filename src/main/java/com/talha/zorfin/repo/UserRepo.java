package com.talha.zorfin.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.talha.zorfin.entity.User;
import com.talha.zorfin.enums.UserRole;
import com.talha.zorfin.enums.UserStatus;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findAllByRole(UserRole role);

    List<User> findAllByStatus(UserStatus status);

    List<User> findAllByRoleAndStatus(UserRole role, UserStatus status);
    
}
