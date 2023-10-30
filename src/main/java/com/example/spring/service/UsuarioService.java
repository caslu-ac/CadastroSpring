package com.example.spring.service;

import com.example.spring.exceptions.BadRequestException;
import com.example.spring.model.Usuario;
import com.example.spring.repository.UsuarioRepository;
import com.example.spring.requests.UsuarioPostRequestBody;
import com.example.spring.requests.UsuarioPutRequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
//    private final Usuario usuario;
    public Usuario salvar(@Valid UsuarioPostRequestBody usuarioPostRequestBody) {
        Usuario usuario = Usuario.builder().nome(usuarioPostRequestBody.getNome())
        .profissao(usuarioPostRequestBody.getProfissao())
        .idade(usuarioPostRequestBody.getIdade()).build();


        return repository.save(usuario);
    }

    public Page<Usuario> page(Pageable pageable) {

        return repository.findAll(pageable);
    }
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }


    public Optional<Usuario> selecionar(Integer id) {

        return repository.findById(id);
    }
    public Usuario selecionarOrThrowBadRequest(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuario não encontrado"));
    }
    public Void editar(Integer id, UsuarioPutRequestBody usuarioPutRequestBody) {

        Optional<Usuario> usuarioExistente = repository.findById(id);
        Usuario usuario = Usuario.builder()
                .nome(usuarioPutRequestBody.getNome())
                .profissao(usuarioPutRequestBody.getProfissao())
                .idade(usuarioPutRequestBody.getIdade()).build();
        usuario.setId(id);

        repository.save(usuario);
        return null;
    }
    public Void deletar(Integer id) {
        repository.deleteById(id);
        return null;
    }
}
