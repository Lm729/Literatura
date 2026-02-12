package com.challenge.Literatura.Repocitorio;

import com.challenge.Literatura.Modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILibrosRepocitorio extends JpaRepository<Libro,Long> {
    List<Libro> findLibroByLanguage(String language);
}