package com.example.spring.util;

import com.example.spring.requests.UsuarioPutRequestBody;

public class UsuarioPutRequestBodyCreator {
    public static com.example.spring.requests.UsuarioPutRequestBody CreateUsuarioPutRequestBody(){
        return UsuarioPutRequestBody.builder()
                .id(UsuarioCreator.adicionarUsuarioComId().getId())
                .nome(UsuarioCreator.adicionarUsuarioComId().getNome())
                .profissao(UsuarioCreator.adicionarUsuarioComId().getProfissao())
                .idade(UsuarioCreator.adicionarUsuarioComId().getIdade())
                .build();


    }
}
