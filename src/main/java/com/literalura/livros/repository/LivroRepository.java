package com.literalura.livros.repository;

import com.literalura.livros.modelos.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<LivroEntity, Integer> {

}