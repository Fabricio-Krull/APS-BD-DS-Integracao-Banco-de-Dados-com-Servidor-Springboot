package com.exemplo.api.produtos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.exemplo.api.produtos.model.Fornecedor;


public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    List<Fornecedor> findByNome(String nome);
    List<Fornecedor> findByNomeContaining(String ParteDoNome);
}
