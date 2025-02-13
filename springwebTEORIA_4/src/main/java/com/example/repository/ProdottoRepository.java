package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Prodotto;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

}
