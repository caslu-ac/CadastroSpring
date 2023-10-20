package com.example.spring.controller;

import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller
@Log4j2
@RequiredArgsConstructor
public class UsuarioController {


    private final UsuarioRepository repository;

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

    @GetMapping("/lista")
    public ModelAndView lista() {
        ModelAndView mav = new ModelAndView("lista");
        Iterable<Usuario> usuarioIterable = repository.findAll();
        mav.addObject("lista", usuarioIterable);
        return mav;

    }
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Integer id) {
        repository.deleteById(id);
        return "redirect:/lista";
    }
    @GetMapping("editar/{id}")
    public String editar(@PathVariable Integer id){
        Optional<Usuario> usuarioOptional = repository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            repository.save(usuario);
        } else {
            // Trate o caso em que o usuário não foi encontrado com o id fornecido.
        }
        return "editar";
    }
    @PostMapping("editar/{id}")
    public String editarUsuario(@PathVariable Integer id, Usuario usuario){
        repository.save(usuario);
        return "redirect: lista";
    }

}

