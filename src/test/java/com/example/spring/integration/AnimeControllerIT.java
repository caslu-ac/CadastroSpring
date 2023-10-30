package com.example.spring.integration;

import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import com.example.spring.requests.UsuarioPostRequestBody;
import com.example.spring.util.UsuarioCreator;
import com.example.spring.util.UsuarioPostRequestBodyCreator;
import com.example.spring.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("List Return List Of Usuarios on page When Sucessful")
    void list_ReturnListOfUsuariosOnPage_WhenSucessful() {
        Usuario usuario = usuarioRepository.save(UsuarioCreator.adicionarUsuarioComId());
        String expectecNome = usuario.getNome();
        PageableResponse<Usuario> usuarios = testRestTemplate.exchange("/page", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Usuario>>() {
                }).getBody();


        Assertions.assertThat(usuarios.toList()).isNotEmpty();
        Assertions.assertThat(usuarios).isNotNull();
        Assertions.assertThat(usuarios.toList().get(0).getNome()).isEqualTo(expectecNome);
    }
    @Test
    @DisplayName("List Return List Of Usuarios When Sucessful")
    void list_ReturnListOfUsuarios_WhenSucessful() {
        Usuario usuario = usuarioRepository.save(UsuarioCreator.adicionarUsuarioComId());
        String expectecNome = usuario.getNome();
        List<Usuario> usuarios = testRestTemplate.exchange("/listarTodos", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Usuario>>() {
                }).getBody();


        Assertions.assertThat(usuarios).isNotEmpty();
        Assertions.assertThat(usuarios).isNotNull()
                .hasSize(1);

        Assertions.assertThat(usuarios.get(0).getNome()).isEqualTo(expectecNome);
    }
    @Test
    @DisplayName("find Return Usuario By Id When Sucessful")
    void find_ReturnUsuarioById_WhenSucessful() {
        Usuario usuario = usuarioRepository.save(UsuarioCreator.adicionarUsuarioComId());
        Integer expectedId = usuario.getId();
        Usuario usuarios = testRestTemplate.getForObject("/find/{id}", Usuario.class, expectedId);

        Assertions.assertThat(usuarios).isNotNull();
        Assertions.assertThat(usuarios.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Save Return Usuario When Sucessful")
    void save_ReturnListUsuario_WhenSucessful() {
        UsuarioPostRequestBody usuarioPostRequestBody = UsuarioPostRequestBodyCreator.CreateUsuarioPostRequestBody();

        ResponseEntity<Usuario> usuariosResponseEntity = testRestTemplate.postForEntity("/criar", usuarioPostRequestBody, Usuario.class);

        Assertions.assertThat(usuariosResponseEntity).isNotNull();
        Assertions.assertThat(usuariosResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(usuariosResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(usuariosResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("Edit Return Usuario Atualizado When Sucessful")
    void edit_ReturnUsuarioAtualizado_WhenSucessful() {
        Usuario usuario = usuarioRepository.save(UsuarioCreator.adicionarUsuarioComId());

        usuario.setNome("new");
        Integer id = usuario.getId();
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/editar/{id}");
        URI uri = builder.buildAndExpand(id).toUri();
        ResponseEntity<Void> usuariosResponseEntity = testRestTemplate.exchange(uri, HttpMethod.PUT,
                new HttpEntity<>(usuario), Void.class);

        Assertions.assertThat(usuariosResponseEntity).isNotNull();

        Assertions.assertThat(usuariosResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


    }
    @Test
    @DisplayName("remove Delete Usuario By Id When Sucessful")
    void remove_DeleteUsuarioById_WhenSucessful() {
        Usuario usuario = usuarioRepository.save(UsuarioCreator.adicionarUsuarioComId());

        usuario.setNome("new");
        Integer id = usuario.getId();
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/deletar/{id}");
        URI uri = builder.buildAndExpand(id).toUri();
        ResponseEntity<Void> usuariosResponseEntity = testRestTemplate.exchange(uri, HttpMethod.DELETE,
                new HttpEntity<>(usuario), Void.class);

        Assertions.assertThat(usuariosResponseEntity).isNotNull();

        Assertions.assertThat(usuariosResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
