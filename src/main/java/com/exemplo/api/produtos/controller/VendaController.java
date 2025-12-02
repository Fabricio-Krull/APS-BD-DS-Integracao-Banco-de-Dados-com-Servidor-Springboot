package com.exemplo.api.produtos.controller;

import java.util.List;
import java.util.Map;

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

import com.exemplo.api.produtos.model.Cliente;
import com.exemplo.api.produtos.model.Produto;
import com.exemplo.api.produtos.model.Venda;
import com.exemplo.api.produtos.repository.ClienteRepository;
import com.exemplo.api.produtos.repository.ProdutoRepository;
import com.exemplo.api.produtos.repository.VendaRepository;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    public VendaController(VendaRepository vendaRepository, ProdutoRepository produtoRepository, ClienteRepository clienteRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/{id}")
    public Venda buscar(@PathVariable Long id) {
        return vendaRepository.findById(id).orElse(null);
    }

    @GetMapping
    public List<Venda> listar() {
        return vendaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Venda venda) {
        if (venda.getItens() != null) {
            venda.getItens().forEach(item -> {
                Produto produto = produtoRepository.findById(item.getProduto().getId()).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
                int quantidadeVendida = Math.min(item.getQuantidade(), produto.getEstoque().getQuantidade());
                if(produto.getEstoque().getQuantidade() <= item.getQuantidade()){
                    item.setVenda(venda);
                    item.setPrecoUnitario(produto.getPreco());
                    item.setPrecoTotal(item.getPrecoUnitario() * quantidadeVendida); // Compra a quantia possível
                    item.setProduto(produto);
                }
                else{
                    item.setVenda(venda);
                    item.setPrecoUnitario(produto.getPreco());
                    item.setPrecoTotal(item.getPrecoUnitario() * item.getQuantidade());
                    item.setProduto(produto);
                }
                
                item.getProduto().getEstoque().setQuantidade(produto.getEstoque().getQuantidade() - quantidadeVendida);
                produtoRepository.save(produto);
            });
        }
        if(venda.getCliente() != null){
            Cliente cliente = clienteRepository.findById(venda.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            venda.setCliente(cliente);

        }
        double valorVenda = 0.0;
        for (var item : venda.getItens()) {
            valorVenda += item.getPrecoTotal();
        }
        venda.setValorTotalVenda(valorVenda);
        if (venda.getValorTotalVenda() > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(vendaRepository.save(venda));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", "Venda inválida: Consulte o estoque para garantir a disponibilidade do produto")); // ou HttpStatus.UNPROCESSABLE_ENTITY
        }
    }

    @PutMapping("/{id}")
    public Venda atualizar(@PathVariable Long id, @RequestBody Venda novaVenda) {
        return vendaRepository.findById(id).map(venda -> {
            // aqui você pode atualizar os campos necessários
            venda.setCliente(novaVenda.getCliente());
            venda.setItens(novaVenda.getItens());
            return vendaRepository.save(venda);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        vendaRepository.deleteById(id);
    }
}
