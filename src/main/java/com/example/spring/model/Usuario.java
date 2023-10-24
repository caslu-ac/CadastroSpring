package com.example.spring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @NotBlank(message = "O campo nome não pode estar vazio")
    private String nome;
    @Column
    @NotBlank(message = "O campo profissão não pode estar vazio")
    private String profissao;
    @Column
    @NotBlank(message = "O campo idade não pode estar vazio")
    private String idade;

}


