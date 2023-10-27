package com.example.spring.requests;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class UsuarioPutRequestBody {
    private Integer id;
    @NotEmpty(message = "O campo nome não pode estar vazio")
    @NotNull
    private String nome;
    @NotEmpty(message = "O campo profissão não pode estar vazio")
    @NotNull
    private String profissao;
    @NotEmpty(message = "O campo idade não pode estar vazio")
    @NotNull
    private Integer idade;
}
