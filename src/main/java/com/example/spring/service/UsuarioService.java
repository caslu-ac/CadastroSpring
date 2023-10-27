package com.example.spring.service;

import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import com.example.spring.requests.UsuarioPostRequestBody;
import com.example.spring.requests.UsuarioPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
//    private final Usuario usuario;
    public Usuario salvar(UsuarioPostRequestBody usuarioPostRequestBody) {
        Usuario usuario = Usuario.builder().nome(usuarioPostRequestBody.getNome())
        .profissao(usuarioPostRequestBody.getProfissao())
        .idade(usuarioPostRequestBody.getIdade()).build();


        return repository.save(usuario);
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

    public Optional<Usuario> editar(Integer id, UsuarioPutRequestBody usuarioPutRequestBody) {

        Optional<Usuario> usuarioExistente = repository.findById(id);
        Usuario usuario = Usuario.builder()
                .nome(usuarioPutRequestBody.getNome())
                .profissao(usuarioPutRequestBody.getProfissao())
                .idade(usuarioPutRequestBody.getIdade()).build();
        usuario.setId(id);
//        Usuario usuarioExistente = usuarioAtualizado.get();
//        usuarioExistente.setId(usuarioAtualizado.getId());

        repository.save(usuario);

        return Optional.of(usuario);
    }
}