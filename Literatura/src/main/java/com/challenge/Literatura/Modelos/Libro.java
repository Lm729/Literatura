package com.challenge.Literatura.Modelos;

import com.challenge.Literatura.Dto.LibroDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Long id;
    @Column(unique = true)
    private String titulo;

    private String language;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    private Long cantidadDescargas;

    public Libro(LibroDTO libroDTO) {
        this.titulo = libroDTO.title();
        this.language = libroDTO.languages().get(0).toUpperCase();
        this.autor = new Autor(libroDTO.authors().get(0));
        this.cantidadDescargas = libroDTO.downloads();
    }

    @Override
    public String toString() {
        return "----- Libro -----" +
                "\n Titulo: " + titulo +
                "\n Autor: " + autor.getName() +
                "\n Idioma: " + language +
                "\n Número de descargas: " + cantidadDescargas +
                "\n-----------------\n";
    }
}

