// Não sei se eu posso remover as rotas de produto do projeto, então eu deixei em um banco à parte, assim como a tabela de usuários
// está igual a do banco do projeto, mas não está sendo inserida nele.

package com.exemplo.api.produtos.model;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double preco;

    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Estoque estoque;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    @JsonIgnoreProperties("produtos") // Exibe a categoria, sem retornar o campo Categoria.produtos
    private Categoria categoria;

    @ManyToMany
    @JoinTable(
        name = "tb_produto_fornecedor",
        joinColumns = @JoinColumn(name = "produto_id"),
        inverseJoinColumns = @JoinColumn(name = "fornecedor_id")
    )
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id") // Usado para
    // exibir o id e nome dos fornecedores, sem causar um loop Produto.Fornecedor -> Fornecedor.Produtos -> ...
    private Set<Fornecedor> fornecedores;

    @OneToMany(mappedBy = "produto")
    @JsonIgnore
    private List<ItemVenda> itensVenda;

    public Produto() {}

    public Produto (String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }


    public Estoque getEstoque() {
        return estoque;
    }
    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }


    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    public Set<Fornecedor> getFornecedores() {
        return fornecedores;
    }
    public void setFornecedores(Set<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    
    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }
    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }




}
