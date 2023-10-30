package com.example.spring.service;

import com.example.spring.exceptions.BadRequestException;
import com.example.spring.service.UsuarioService;
import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import com.example.spring.requests.UsuarioPostRequestBody;
import com.example.spring.requests.UsuarioPutRequestBody;
import com.example.spring.util.UsuarioCreator;
import com.example.spring.util.UsuarioPostRequestBodyCreator;
import com.example.spring.util.UsuarioPutRequestBodyCreator;
import jakarta.validation.ConstraintViolationException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@ExtendWith(SpringExtension.class)
public class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService Service;
    @Mock
    private UsuarioRepository repositoryMock;


    @BeforeEach
    void setUp() {
        BDDMockito.when(repositoryMock.save(ArgumentMatchers.any(Usuario.class)))
                .thenReturn(UsuarioCreator.criarUsuarioComId());

        BDDMockito.when(repositoryMock.findAll())
                .thenReturn(List.of(UsuarioCreator.criarUsuario()));
        BDDMockito.when(repositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.ofNullable(UsuarioCreator.adicionarUsuarioComId()));
        BDDMockito.doNothing().when(repositoryMock).delete(ArgumentMatchers.any(Usuario.class));

    }

    @Test
    @DisplayName("Save Return Usuario When Sucessful")
    void save_ReturnListUsuario_WhenSucessful() {
        Integer expectecId = UsuarioCreator.criarUsuarioComId().getId();
        Usuario usuarios = Service.salvar(UsuarioPostRequestBodyCreator
                .CreateUsuarioPostRequestBody());

        Assertions.assertThat(usuarios.getId()).isEqualTo(expectecId);
    }

    @Test
    @DisplayName("List Return Usuario By Id When Sucessful")
    void list_ReturnUsuarioById_WhenSucessful() {
        Integer expectedId = UsuarioCreator.criarUsuarioComId().getId();
        Optional<Usuario> usuario = Service.selecionar(1);

        Assertions.assertThat(usuario).isNotEmpty();
        Assertions.assertThat(usuario).isNotNull();
        Assertions.assertThat(usuario.get().getId()).isEqualTo(expectedId);
    }
    @Test
    @DisplayName("find Return Usuario ThrowsBadRequestException By Id When Usuario Is Not Found")
    void find_ReturnUsuarioById_ThrowsBadRequestException_WhenSucessfulUsuarioIsNotFound() {
        BDDMockito.when(repositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.empty());
        Integer expectedId = UsuarioCreator.criarUsuarioComId().getId();
        Optional<Usuario> usuario = Service.selecionar(1);

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                        .isThrownBy(()-> Service.selecionarOrThrowBadRequest(1));

    }
    @Test
    @DisplayName("List Return List Of Usuarios When Sucessful")
    void list_ReturnListOfUsuarios_WhenSucessful() {
        String expectecNome = UsuarioCreator.criarUsuario().getNome();
        List<Usuario> usuarios = Service.listarTodos();

        Assertions.assertThat(usuarios).isNotEmpty();
        Assertions.assertThat(usuarios).isNotNull();
        Assertions.assertThat(usuarios.get(0).getNome()).isEqualTo(expectecNome);
    }



    @Test
    @DisplayName("Edit Return Usuario Atualizado When Sucessful")
    void edit_ReturnUsuarioAtualizado_WhenSucessful() {
        Assertions.assertThatCode(() -> Service.editar(1, UsuarioPutRequestBodyCreator.CreateUsuarioPutRequestBody()))
                .doesNotThrowAnyException();

    }
    @Test
    @DisplayName("remove Delete Usuario By Id When Sucessful")
    void remove_DeleteUsuarioById_WhenSucessful() {
        Assertions.assertThatCode(() -> Service.deletar(1))
                .doesNotThrowAnyException();
    }


}
