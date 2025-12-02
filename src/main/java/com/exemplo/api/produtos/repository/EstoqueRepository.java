package com.exemplo.api.produtos.repository;

// import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.exemplo.api.produtos.model.Estoque;


public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}
