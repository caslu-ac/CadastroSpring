package com.example.spring.util;

import com.example.spring.requests.UsuarioPostRequestBody;

public class UsuarioPostRequestBodyCreator {
    public static UsuarioPostRequestBody CreateUsuarioPostRequestBody(){
        return UsuarioPostRequestBody.builder()
                .nome(UsuarioCreator.criarUsuario().getNome())
                .profissao(UsuarioCreator.criarUsuario().getProfissao())
                .idade(UsuarioCreator.criarUsuario().getIdade())
                .build();


    }
}
