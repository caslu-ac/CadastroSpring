package com.example.spring.repository;

import com.example.spring.model.LucasUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LucasUserRepository extends JpaRepository<LucasUser, Integer> {
    LucasUser findByUsername(String nome);
}
