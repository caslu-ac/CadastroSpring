package com.example.spring.controller;

import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@Log4j2
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping(path = "/criar")
    @ResponseStatus(code = HttpStatus.CREATED)
    public String cadastrar(Usuario usuario) {
        repository.save(usuario);
        return "redirect:/lista";
    }
    @GetMapping("listar")
    public List<Usuario> lista(){
     return null;
    }
}

