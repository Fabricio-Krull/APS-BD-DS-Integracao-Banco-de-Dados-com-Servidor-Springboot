package com.exemplo.api.produtos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.exemplo.api.produtos.model.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByNome(String nome);
    List<Categoria> findByNomeContaining(String ParteDoNome);
}
