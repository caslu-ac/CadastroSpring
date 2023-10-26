package com.example.spring.controller;

import com.example.spring.UsuarioCreator;
import com.example.spring.model.Usuario;
import com.example.spring.service.UsuarioService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
class UsuarioControllerTest {
    @InjectMocks
    private UsuarioController usuarioController;
    @Mock
    private UsuarioService ServiceMock;
    @BeforeEach
    void setUp(){
       List<Usuario> usuarios = List.of(UsuarioCreator.criarUsuario());
       BDDMockito.when(ServiceMock.listar())
               .thenReturn(usuarios);
//       BDDMockito.when(usuarioService.li(ArgumentMatchers.any(Usuario)))
//               .thenReturn(List.of(UsuarioCreator.criarUsuario()));
//       BDDMockito.when(usuarioService.listar())
//               .thenReturn(List.of(UsuarioCreator.criarUsuario()));
    }

//    @Test
//    @DisplayName("List Return List Of Usuarios When Sucessful")
//    void list_ReturnListOfUsuarios_WhenSucessful(){
//        String expectecNome = UsuarioCreator.criarUsuario().getNome();
//        String usuarios = usuarioController.lista().getModelMap().values().stream().collect(Collectors.toList()).get(0).toString();
//        Assertions.assertThat(usuarios).isNotEmpty();
//        Assertions.assertThat(usuarios).isNotNull();
//        Assertions.assertThat(usuarios).isEqualTo(expectecNome);
//
//
//    }

}