package com.exemplo.api.produtos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exemplo.api.produtos.model.Venda;


public interface VendaRepository extends JpaRepository<Venda, Long> {
}