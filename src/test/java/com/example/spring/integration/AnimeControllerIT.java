package com.example.spring.integration;

import com.example.spring.model.LucasUser;
import com.example.spring.model.Usuario;
import com.example.spring.repository.LucasUserRepository;
import com.example.spring.repository.UsuarioRepository;
import com.example.spring.requests.UsuarioPostRequestBody;
import com.example.spring.util.UsuarioCreator;
import com.example.spring.util.UsuarioPostRequestBodyCreator;
import com.example.spring.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
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
    @Qualifier(value = "testRestTemplateRoleUserCreator")
    private TestRestTemplate testRestTemplateRoleUser;
    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdminCreator")
    private TestRestTemplate testRestTemplateRoleAdmin;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LucasUserRepository lucasUserRepository;
    @Autowired
    private static final LucasUser admin = LucasUser.builder()
            .nome("lucas")
            .username("lucas1")
            .password("{bcrypt}$2a$10$m3dXupkO3Hs1wRGV6FBDtOHYRLTdevVgWeRK7nFtaCyA0Homg0Axy")
            .authorities("ROLE_USER,ROLE_ADMIN")
            .build();
    @Autowired
    private static final LucasUser user = LucasUser.builder()
            .nome("User")
            .username("user1")
            .password("{bcrypt}$2a$10$m3dXupkO3Hs1wRGV6FBDtOHYRLTdevVgWeRK7nFtaCyA0Homg0Axy")
            .authorities("ROLE_USER")
            .build();

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleUserCreator")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("user1", "user1");
            return new TestRestTemplate(restTemplateBuilder);

        }

        @Bean(name = "testRestTemplateRoleAdminCreator")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("lucas1", "user1");
            return new TestRestTemplate(restTemplateBuilder);

        }
    }

    @Test
    @DisplayName("List Return List Of Usuarios on page When Sucessful")
    void list_ReturnListOfUsuariosOnPage_WhenSucessful() {
        Usuario usuario = usuarioRepository.save(UsuarioCreator.adicionarUsuarioComId());
        lucasUserRepository.save(user);
        String expectecNome = usuario.getNome();
        PageableResponse<Usuario> usuarios = testRestTemplateRoleUser.exchange("/page", HttpMethod.GET, null,
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
        lucasUserRepository.save(user);
        String expectecNome = usuario.getNome();
        List<Usuario> usuarios = testRestTemplateRoleUser.exchange("/listarTodos", HttpMethod.GET, null,
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
        lucasUserRepository.save(user);
        Integer expectedId = usuario.getId();
        Usuario usuarios = testRestTemplateRoleUser.getForObject("/find/{id}", Usuario.class, expectedId);

        Assertions.assertThat(usuarios).isNotNull();
        Assertions.assertThat(usuarios.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Save Return Usuario When Sucessful")
    void save_ReturnListUsuario_WhenSucessful() {
        UsuarioPostRequestBody usuarioPostRequestBody = UsuarioPostRequestBodyCreator.CreateUsuarioPostRequestBody();
        lucasUserRepository.save(admin);
        ResponseEntity<Usuario> usuariosResponseEntity = testRestTemplateRoleAdmin.postForEntity("/admin/criar", usuarioPostRequestBody, Usuario.class);

        Assertions.assertThat(usuariosResponseEntity).isNotNull();
        Assertions.assertThat(usuariosResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(usuariosResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(usuariosResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("Save Returns 403 When User Is Not Admin")
    void save_Returns403_WhenUserIsNotAdmin() {
        UsuarioPostRequestBody usuarioPostRequestBody = UsuarioPostRequestBodyCreator.CreateUsuarioPostRequestBody();
        lucasUserRepository.save(user);
        ResponseEntity<Usuario> usuariosResponseEntity = testRestTemplateRoleUser.postForEntity("/admin/criar", usuarioPostRequestBody, Usuario.class);


        Assertions.assertThat(usuariosResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("Edit Return Usuario Atualizado When Sucessful")
    void edit_ReturnUsuarioAtualizado_WhenSucessful() {
        Usuario usuario = usuarioRepository.save(UsuarioCreator.adicionarUsuarioComId());
        lucasUserRepository.save(admin);
        usuario.setNome("new");
        Integer id = usuario.getId();
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/admin/editar/{id}");
        URI uri = builder.buildAndExpand(id).toUri();
        ResponseEntity<Void> usuariosResponseEntity = testRestTemplateRoleAdmin.exchange(uri, HttpMethod.PUT,
                new HttpEntity<>(usuario), Void.class);

        Assertions.assertThat(usuariosResponseEntity).isNotNull();

        Assertions.assertThat(usuariosResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


    }

    @Test
    @DisplayName("Edit Returns 403 When Role Is Not Admin")
    void edit_Returns403_WhenRoleIsNotAdmin() {
        Usuario usuario = usuarioRepository.save(UsuarioCreator.adicionarUsuarioComId());
        lucasUserRepository.save(user);
        usuario.setNome("new");
        Integer id = usuario.getId();
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/admin/editar/{id}");
        URI uri = builder.buildAndExpand(id).toUri();
        ResponseEntity<Void> usuariosResponseEntity = testRestTemplateRoleUser.exchange(uri, HttpMethod.PUT,
                new HttpEntity<>(usuario), Void.class);


        Assertions.assertThat(usuariosResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);


    }

    @Test
    @DisplayName("remove Returns 403 When Role Is Not Admin")
    void remove_Returns403_WhenRoleIsNotAdmin() {
        Usuario usuario = usuarioRepository.save(UsuarioCreator.adicionarUsuarioComId());
        lucasUserRepository.save(user);
        usuario.setNome("new");
        Integer id = usuario.getId();
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/admin/deletar/{id}");
        URI uri = builder.buildAndExpand(id).toUri();
        ResponseEntity<Void> usuariosResponseEntity = testRestTemplateRoleUser.exchange(uri, HttpMethod.DELETE,
                new HttpEntity<>(usuario), Void.class);

        Assertions.assertThat(usuariosResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @DisplayName("remove Delete Usuario By Id When Sucessful")
    void remove_DeleteUsuarioById_WhenSucessful() {
        Usuario usuario = usuarioRepository.save(UsuarioCreator.adicionarUsuarioComId());
        lucasUserRepository.save(admin);
        usuario.setNome("new");
        Integer id = usuario.getId();
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/admin/deletar/{id}");
        URI uri = builder.buildAndExpand(id).toUri();
        ResponseEntity<Void> usuariosResponseEntity = testRestTemplateRoleAdmin.exchange(uri, HttpMethod.DELETE,
                new HttpEntity<>(usuario), Void.class);


        Assertions.assertThat(usuariosResponseEntity).isNotNull();

        Assertions.assertThat(usuariosResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

}
