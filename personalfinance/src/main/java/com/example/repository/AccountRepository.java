package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Account;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long userId);
}