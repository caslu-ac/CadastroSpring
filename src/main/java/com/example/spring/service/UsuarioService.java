package com.example.spring.service;


import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UsuarioService {
    private UsuarioRepository repository;
    private Usuario usuario;


    public void salvar(){
        repository.save(usuario);
    }

    public List<Usuario> listar(){
        List<Usuario> usuarios = repository.findAll();
        return usuarios;
    }

    public void deletar(Integer id){
        usuario.getId();
        repository.deleteById(id);
    }
    public Optional<Usuario> selecionar(Integer id){
       Optional<Usuario> usuario =  repository.findById(id);
       return usuario;

    }
}
