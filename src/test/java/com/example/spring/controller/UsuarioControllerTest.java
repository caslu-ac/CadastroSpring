package com.example.spring.controller;

import com.example.spring.requests.UsuarioPutRequestBody;
import com.example.spring.util.UsuarioCreator;
import com.example.spring.model.Usuario;
import com.example.spring.requests.UsuarioPostRequestBody;
import com.example.spring.service.UsuarioService;
import com.example.spring.util.UsuarioPostRequestBodyCreator;
import com.example.spring.util.UsuarioPutRequestBodyCreator;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class UsuarioControllerTest {
    @InjectMocks
    private UsuarioController usuarioController;
    @Mock
    private UsuarioService ServiceMock;


    @BeforeEach
    void setUp() {
        PageImpl<Usuario> usuarioPage = new PageImpl<>(List.of(UsuarioCreator.criarUsuario()));
        BDDMockito.when(ServiceMock.page(ArgumentMatchers.any()))
                .thenReturn(usuarioPage);
        BDDMockito.when(ServiceMock.listarTodos())
                .thenReturn(List.of(UsuarioCreator.criarUsuario()));
        BDDMockito.when(ServiceMock.selecionar(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.ofNullable(UsuarioCreator.adicionarUsuarioComId()));
        BDDMockito.when(ServiceMock.salvar(ArgumentMatchers.any(UsuarioPostRequestBody.class)))
                .thenReturn(UsuarioCreator.criarUsuarioComId());
        BDDMockito.doNothing().when(ServiceMock).editar(
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.any(UsuarioPutRequestBody.class)
        ); BDDMockito.doNothing().when(ServiceMock).deletar(ArgumentMatchers.anyInt());
}

    @Test
    @DisplayName("List Return List Of Usuarios on page When Sucessful")
    void list_ReturnListOfUsuariosOnPage_WhenSucessful() {
        String expectecNome = UsuarioCreator.criarUsuario().getNome();
        Page<Usuario> usuarios = usuarioController.page(null).getBody();

        Assertions.assertThat(usuarios).isNotNull();

        Assertions.assertThat(usuarios.toList()).isNotEmpty().hasSize(1);

        Assertions.assertThat(usuarios.toList().get(0).getNome()).isEqualTo(expectecNome);
    }

    @Test
    @DisplayName("List Return List Of Usuarios When Sucessful")
    void list_ReturnListOfUsuarios_WhenSucessful() {
        String expectecNome = UsuarioCreator.criarUsuario().getNome();
        List<Usuario> usuarios = usuarioController.listarTodos();

        Assertions.assertThat(usuarios).isNotEmpty();
        Assertions.assertThat(usuarios).isNotNull();
        Assertions.assertThat(usuarios.get(0).getNome()).isEqualTo(expectecNome);
    }
    @Test
    @DisplayName("List Return Usuario By Id When Sucessful")
    void list_ReturnUsuarioById_WhenSucessful() {
        Integer expectedId = UsuarioCreator.criarUsuarioComId().getId();
        Optional<Usuario> usuario = usuarioController.selecionar(1).getBody();

        Assertions.assertThat(usuario).isNotEmpty();
        Assertions.assertThat(usuario).isNotNull();
        Assertions.assertThat(usuario.get().getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Save Return Usuario When Sucessful")
    void save_ReturnListUsuario_WhenSucessful() {
        Integer expectecId = UsuarioCreator.criarUsuarioComId().getId();
        Usuario usuarios = usuarioController.cadastrar(UsuarioPostRequestBodyCreator
                .CreateUsuarioPostRequestBody()).getBody();

        Assertions.assertThat(usuarios.getId()).isEqualTo(expectecId);
    }

    @Test
    @DisplayName("Edit Return Usuario Atualizado When Sucessful")
    void edit_ReturnUsuarioAtualizado_WhenSucessful() {
        Assertions.assertThatCode(() -> usuarioController.editarUsuario(1,UsuarioPutRequestBodyCreator.CreateUsuarioPutRequestBody()))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = usuarioController.editarUsuario(1,UsuarioPutRequestBodyCreator.CreateUsuarioPutRequestBody());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
    @Test
    @DisplayName("remove Delete Usuario By Id When Sucessful")
    void remove_DeleteUsuarioById_WhenSucessful() {
        Assertions.assertThatCode(() -> usuarioController.deletar(1))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = usuarioController.deletar(1);
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }



}