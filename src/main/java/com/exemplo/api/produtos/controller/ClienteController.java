// Não sei se eu posso remover as rotas de produto do projeto, então eu deixei em um banco à parte, assim como a tabela de usuários
// está igual a do banco do projeto, mas não está sendo inserida nele.

package com.exemplo.api.produtos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.api.produtos.model.Cliente;
import com.exemplo.api.produtos.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @GetMapping("")
    public List<Cliente> listar(){
        return repository.findAll();
    }

    @PostMapping
    public Cliente criar(@RequestBody Cliente usuario) {
        return repository.save(usuario);
    }

    @PatchMapping("/{id}")
    public Cliente atualizar(@PathVariable Long id, @RequestBody Cliente novoUsuario) {
        return repository.findById(id).map(usuario -> {
            if(novoUsuario.getNome() != null && !novoUsuario.getNome().isBlank() && !(novoUsuario.getNome().equals(usuario.getNome()))){
                usuario.setNome(novoUsuario.getNome());
            }

            if(novoUsuario.getEmail() != null && !novoUsuario.getEmail().isBlank() && !(novoUsuario.getEmail().equals(usuario.getEmail()))){
                usuario.setEmail(novoUsuario.getEmail());
            }

            if(novoUsuario.getDataNascimento() != null && !novoUsuario.getDataNascimento().isBlank() && !(novoUsuario.getDataNascimento().equals(usuario.getDataNascimento()))){
                usuario.setDataNascimento(novoUsuario.getDataNascimento());
            }

            return repository.save(usuario);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }

}