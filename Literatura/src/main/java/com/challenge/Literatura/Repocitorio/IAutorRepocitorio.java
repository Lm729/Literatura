package com.challenge.Literatura.Repocitorio;

import com.challenge.Literatura.Modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAutorRepocitorio extends JpaRepository<Autor,Long> {
    Autor findAutorsByName(String name);
    @Query(value = "SELECT * FROM autors WHERE :year >= birth_year AND :year <= death_year", nativeQuery = true)
    List<Autor> findAutorBetweenYear(int year);
}
