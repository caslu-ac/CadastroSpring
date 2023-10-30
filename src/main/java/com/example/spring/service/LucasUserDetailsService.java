package com.example.spring.service;

import com.example.spring.repository.LucasUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LucasUserDetailsService implements UserDetailsService {
    private final LucasUserRepository lucasUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(lucasUserRepository.findByUsername(username))
                .orElseThrow(()-> new UsernameNotFoundException("usuario não encontrado"));
    }
}
