package com.example.spring.controller;

import com.example.spring.UsuarioCreator;
import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UsuarioControllerTest {
    @InjectMocks
    private UsuarioController usuarioController;
    @Mock
    private UsuarioRepository repositoryMock;
    @BeforeEach
    void setUp() {
        BDDMockito.when(repositoryMock.findAll())
                .thenReturn(List.of(UsuarioCreator.criarUsuarioComId()));
        BDDMockito.when(repositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.ofNullable(UsuarioCreator.criarUsuarioComId()));
        BDDMockito.when(usuarioController.cadastrar(ArgumentMatchers.any(Usuario.class)))
                .thenReturn(String.valueOf((UsuarioCreator.criarUsuarioComId())));
    }
    @Test
    @DisplayName("List Return List Of Usuarios When Sucessfull")
    void List_ReturnListOfUsuarios_WhenSucessfull(){
        String expectedName = UsuarioCreator.criarUsuario().getNome();
        List<Usuario> usuarioList = repositoryMock.findAll();

        org.assertj.core.api.Assertions.assertThat(usuarioList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        org.assertj.core.api.Assertions.assertThat(usuarioList.get(0).getNome().equals(expectedName));
    }
    @Test
    @DisplayName("FindById Return Usuario When Sucessfull")
    void FindById_ReturnUsuario_WhenSucessfull(){
        Integer expectedId = UsuarioCreator.criarUsuario().getId();
        Optional<Usuario> usuario = repositoryMock.findById(1);

        org.assertj.core.api.Assertions.assertThat(usuario)
                .isNotNull();
        org.assertj.core.api.Assertions.assertThat(usuario.get().getId().equals(expectedId));
    }
    @Test
    @DisplayName("Save Return Usuario When Sucessfull")
    void save_ReturnUsuario_WhenSucessfull(){
        Integer expectedId = UsuarioCreator.criarUsuario().getId();
        Usuario usuario = repositoryMock.save(new Usuario());

        org.assertj.core.api.Assertions.assertThat(usuario)
                .isNotNull();
        org.assertj.core.api.Assertions.assertThat(usuario.equals(expectedId));
    }
}