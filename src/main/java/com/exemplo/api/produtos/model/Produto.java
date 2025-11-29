// Não sei se eu posso remover as rotas de produto do projeto, então eu deixei em um banco à parte, assim como a tabela de usuários
// está igual a do banco do projeto, mas não está sendo inserida nele.

package com.exemplo.api.produtos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double preco;

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

    

}
