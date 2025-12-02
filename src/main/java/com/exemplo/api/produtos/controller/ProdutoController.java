package com.exemplo.api.produtos.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.api.produtos.model.Fornecedor;
import com.exemplo.api.produtos.model.Produto;
import com.exemplo.api.produtos.repository.CategoriaRepository;
import com.exemplo.api.produtos.repository.FornecedorRepository;
import com.exemplo.api.produtos.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;

    // GET /api/produtos
    @GetMapping
    public List<Produto> getAllProdutos(){
        return produtoRepository.findAll();
    }

    // GET /api/produtos/id
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id){

        return produtoRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    // POST /api/produtos
    @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto){

        if (produto.getCategoria() == null || produto.getCategoria().getId() == null) return ResponseEntity.badRequest().build();

        categoriaRepository.findById(produto.getCategoria().getId()).ifPresent(produto::setCategoria);

        if (produto.getFornecedores() != null && !produto.getFornecedores().isEmpty()) {

            List<Fornecedor> fornecedores = new ArrayList<>();

            for (Fornecedor f : produto.getFornecedores()) {
                fornecedorRepository.findById(f.getId())
                    .ifPresent(fornecedores::add);
            }

            produto.setFornecedores(new HashSet<>(fornecedores));
        }

        if (produto.getEstoque() != null) {
            produto.getEstoque().setProduto(produto);
        }

        Produto savedProduto = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);

    }

    // PUT /api/produtos/id
    // PUT -> atualizar dados assim como o PATCH
    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produtoDetails){
        
        return produtoRepository.findById(id).map(produto -> {

            produto.setNome(produtoDetails.getNome());
            Produto updatedProduto = produtoRepository.save(produto);
            return ResponseEntity.ok(updatedProduto);

        }).orElse(ResponseEntity.notFound().build());

    }

    // DELETE /api/produtos/id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id){
        if(!produtoRepository.existsById(id)) return ResponseEntity.notFound().build();
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
