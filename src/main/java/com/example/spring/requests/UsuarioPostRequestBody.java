package com.example.spring.requests;


import lombok.Data;
@Data
public class UsuarioPostRequestBody {
    private String nome;
    private String profissao;
    private Integer idade;
}
