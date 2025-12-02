package com.exemplo.api.produtos.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.api.produtos.model.Estoque;
import com.exemplo.api.produtos.repository.EstoqueRepository;

@RestController
@RequestMapping("/api/estoques")
public class EstoqueController {
    private final EstoqueRepository repository;

    public EstoqueController(EstoqueRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Estoque criar(@RequestBody Estoque estoque) {
        return repository.save(estoque);
    }

    @GetMapping
    public List<Estoque> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Estoque buscar(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Estoque atualizar(@PathVariable Long id, @RequestBody Estoque novoEstoque) {
        return repository.findById(id).map(estoque -> {
            estoque.setQuantidade(novoEstoque.getQuantidade());
            estoque.setProduto(novoEstoque.getProduto());
            return repository.save(estoque);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
