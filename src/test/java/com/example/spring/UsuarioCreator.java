package com.example.spring;

import com.example.spring.model.Usuario;

public class UsuarioCreator {
    public static Usuario criarUsuario(){
        return Usuario.builder()
                .nome("lucas")
                .profissao("estag")
                .idade("21")
                .build();
    }
    public static Usuario criarUsuarioComId(){
        return Usuario.builder()
                .id(Integer.valueOf("2"))
                .nome("lucas")
                .profissao("estag")
                .idade("21")
                .build();
    }
    public static Usuario adicionarUsuarioComId(){
        return Usuario.builder()
                .id(Integer.valueOf("2"))
                .nome("roger")
                .profissao("estag")
                .idade("21")
                .build();
    }

}
