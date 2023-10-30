package com.example.spring.requests;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
