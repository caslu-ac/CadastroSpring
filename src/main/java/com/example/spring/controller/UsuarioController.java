package com.example.spring.controller;

import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import com.example.spring.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> lista() {
        List<Usuario> usuarios = service.listar();

        return ResponseEntity.ok(usuarios);

    }

    @GetMapping("find/{id}")
    public ResponseEntity<Optional<Usuario>> editar(@PathVariable Integer id) {
        Optional<Usuario> usuario = service.selecionar(id);
        return ResponseEntity.ok(usuario);

    }

    @Transactional
    @PostMapping(path = "/criar")
//    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        service.salvar(usuario);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>deletar(@PathVariable Integer id) {
        service.deletar(id);
        return new  ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping
    public ResponseEntity<Void> editar(@RequestBody Usuario usuario, Integer id) {
        service.editar(usuario.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



