package com.example.foodwed.repository;

import com.example.foodwed.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {
    boolean existsByEmailAddress(String emailAddress);
}
