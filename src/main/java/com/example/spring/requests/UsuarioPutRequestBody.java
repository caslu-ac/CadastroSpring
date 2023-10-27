package com.example.spring.requests;
import lombok.Data;
@Data
public class UsuarioPutRequestBody {
    private Integer id;
    private String nome;
    private String profissao;
    private Integer idade;
}
