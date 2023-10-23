package com.example.spring.controller;

import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import com.example.spring.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


@Controller
@Log4j2
//@RequestMapping("usuario")
@RequiredArgsConstructor
public class UsuarioController {


    private final UsuarioRepository repository;


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping(path = "/criar")
//    @ResponseStatus(code = HttpStatus.CREATED)
    public String cadastrar(Usuario usuario) {
        repository.save(usuario);
        return "redirect:/lista";
    }

    @GetMapping("/lista")
    public ModelAndView lista() {
        ModelAndView mav = new ModelAndView("lista");
        List<Usuario> usuarios = repository.findAll();
        mav.addObject("lista", usuarios);
        return mav;

    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Integer id) {
        repository.deleteById(id);
        return "redirect:/lista";
    }

    @GetMapping("editar/{id}")
    public ModelAndView editar(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("editar");
        Optional<Usuario> usuarioVelho = repository.findById(id);
        Usuario usuario = usuarioVelho.get();
        mav.addObject("usuario", usuario);
        return mav;

    }

    @PostMapping("update/{id}")
    public String editar(@PathVariable("id") Integer id, Usuario usuarioAtualizado) {
        usuarioAtualizado.setId(id);
        repository.save(usuarioAtualizado);
        return "redirect:/lista";
    }
}



