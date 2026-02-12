package com.challenge.Literatura.Modelos;

import com.challenge.Literatura.Dto.AutorDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "autors")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Long id;

    @Column(unique = true)
    private String name;

    private Integer birthYear;

    private Integer deathYear;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> Libros;

    public Autor(AutorDTO AutorDTO){
        this.name = AutorDTO.authorName();
        this.birthYear = AutorDTO.birthYear();
        this.deathYear = AutorDTO.deathYear();
    }

    @Override
    public String toString() {
        return "----- Autor -----" +
                "\n Nombre: " + name +
                "\n Fecha de Nacimiento: " + birthYear +
                "\n Fecha de Fallecimiento: " + deathYear +
                "\n Libros: " + Libros.stream().map(b -> b.getTitulo()).collect(Collectors.toList()) +
                "\n---------------\n";
    }
}

