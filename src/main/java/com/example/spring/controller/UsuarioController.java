package com.example.spring.controller;

import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import com.example.spring.requests.UsuarioPostRequestBody;
import com.example.spring.requests.UsuarioPutRequestBody;
import com.example.spring.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@Log4j2
//@RequestMapping("usuario")
@RequiredArgsConstructor

public class UsuarioController {


    private final UsuarioRepository repository;
    private final UsuarioService service;


//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }

    @GetMapping("/page")
    public ResponseEntity<Page<Usuario>> page(Pageable pageable) {
        return ResponseEntity.ok(service.page(pageable));

    }@GetMapping("/listarTodos")
    public List<Usuario> listarTodos() {
        List<Usuario> usuario = service.listarTodos();
        return ResponseEntity.ok(usuario).getBody();

    }

    @GetMapping("find/{id}")
    public ResponseEntity<Optional<Usuario>> selecionar(@PathVariable Integer id) {
        Optional<Usuario> usuario = service.selecionar(id);
        return ResponseEntity.ok(usuario);

    }

    @Transactional
    @PostMapping(path = "/criar")
//    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Usuario> cadastrar(@RequestBody UsuarioPostRequestBody usuarioPostRequestBody) {
        return new ResponseEntity<>(service.salvar(usuarioPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void>deletar(@PathVariable Integer id) {
        service.deletar(id);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Void> editarUsuario(@PathVariable Integer id, @RequestBody UsuarioPutRequestBody usuarioPutRequestBody) {
        usuarioPutRequestBody.setId(id);
        service.editar(id, usuarioPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



