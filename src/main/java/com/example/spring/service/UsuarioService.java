package com.example.spring.service;

import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
//    private final Usuario usuario;

    public void salvar(Usuario usuario) {
        repository.save(usuario);
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario deletar(Integer id) {
        repository.deleteById(id);
        return null;
    }

    public Optional<Usuario> selecionar(Integer id) {

        return repository.findById(id);
    }
    public void editar(Integer id){
        Optional<Usuario> usuario = repository.findById(id);

        return repository.save(usuario);
    }

}