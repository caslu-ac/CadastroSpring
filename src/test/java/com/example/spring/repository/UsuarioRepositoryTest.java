package com.example.spring.repository;

import com.example.spring.model.Usuario;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static com.example.spring.UsuarioCreator.criarUsuario;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("teste para o UsuarioRepository")
@Log4j2
class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Test
    @DisplayName("Save persist usuario when sucessfull")
    void Save_PersistUsuario_WhenSucessful() {
        Usuario usuario = criarUsuario();
        Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo(usuario.getNome());
    }
    @Test
    @DisplayName("Save update usuario when sucessfull")
    void Save_UpdateUsuario_WhenSucessful() {
        Usuario usuario = criarUsuario();
        Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
        usuarioSalvo.setNome("teste");
        usuarioSalvo.setProfissao("testador");
        usuarioSalvo.setIdade(25);
        log.info(usuarioSalvo.getNome());
        Usuario usuarioAtualizado = this.usuarioRepository.save(usuarioSalvo);
        Assertions.assertThat(usuarioAtualizado).isNotNull();
        Assertions.assertThat(usuarioAtualizado.getId()).isNotNull();
        Assertions.assertThat(usuarioAtualizado.getNome()).isEqualTo(usuarioSalvo.getNome());
    }
    @Test
    @DisplayName("Delete remove usuario when sucessfull")
    void Delete_RemoveUsuario_WhenSucessful() {
        Usuario usuario = criarUsuario();
        Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
        this.usuarioRepository.delete(usuarioSalvo);
        Optional<Usuario> usuarioOptional =  this.usuarioRepository.findById(usuarioSalvo.getId());
        Assertions.assertThat(usuarioOptional.isEmpty());
    }
    @Test
    @DisplayName("Find By name return list of usuario when sucessfull")
    void FindByName_ReturnListOfUsuario_WhenSucessful() {
        Usuario usuario = criarUsuario();
        Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
        String nome = usuarioSalvo.getNome();
        List<Usuario> usuarios = this.usuarioRepository.findByNome(nome);
        Assertions.assertThat(usuarios).isNotEmpty();
        Assertions.assertThat(usuarios).contains(usuarioSalvo);
    }
    @Test
    @DisplayName("Find By name return empty list when no usuario found")
    void FindByName_ReturnEmptyList_WhenusuariosIsNotFound() {
        Usuario usuario = criarUsuario();
        Usuario usuarioSalvo = this.usuarioRepository.save(usuario);
        List<Usuario> usuarios = this.usuarioRepository.findByNome("nome");
        Assertions.assertThat(usuarios).isEmpty();


//        Assertions.assertThat(usuario);
    }
    @Test
    @DisplayName("Save Throw ConstraintViolationException when name is empty")
    void Save_ThrowConstraintViolationException_WhenNameIsEmpty() {
        Usuario usuario = criarUsuario();
        usuario.setNome("");
        Assertions.assertThatThrownBy(() -> this.usuarioRepository.save(usuario))
                .isInstanceOf(ConstraintViolationException.class);
    }

}