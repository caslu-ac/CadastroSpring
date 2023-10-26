package com.example.spring.controller;

import com.example.spring.exceptions.BadRequestException;
import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import com.example.spring.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    private final UsuarioService service;


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @Transactional
    @PostMapping(path = "/criar")
//    @ResponseStatus(code = HttpStatus.CREATED)
    public String cadastrar(@Valid Usuario usuario) {
        service.salvar(usuario);
//        if(usuario.getNome() == "") throw  new BadRequestException("O campo nome não pode estar vazio");
//        if(usuario.getProfissao() == "") throw  new BadRequestException("O campo profissão não pode estar vazio");
//        if(usuario.getIdade() == "") throw  new BadRequestException("O campo idade não pode estar vazio");
        return "redirect:/lista";
    }

    @GetMapping("/lista")
    public ModelAndView lista() {
        ModelAndView mav = new ModelAndView("lista");
        List<Usuario> usuarios = service.listar();
        mav.addObject("lista", usuarios);
        return mav;

    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Integer id) {
        service.deletar(id);
        return "redirect:/lista";
    }

    @GetMapping("editar/{id}")
    public ModelAndView editar(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("editar");
        Optional<Usuario> usuarioVelho = service.selecionar(id);
        Usuario usuario = usuarioVelho.get();
        mav.addObject("usuario", usuario);
        return mav;

    }

    @PostMapping("update/{id}")
    public String editar(@PathVariable("id") Integer id, Usuario usuarioAtualizado) {
        usuarioAtualizado.setId(id);
        service.salvar(usuarioAtualizado);
        return "redirect:/lista";
    }
}



