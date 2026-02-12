package com.challenge.Literatura.Principal;

import com.challenge.Literatura.Dto.AutorDTO;
import com.challenge.Literatura.Dto.JsonDTO;
import com.challenge.Literatura.Modelos.Autor;
import com.challenge.Literatura.Modelos.Libro;
import com.challenge.Literatura.Repocitorio.IAutorRepocitorio;
import com.challenge.Literatura.Repocitorio.ILibrosRepocitorio;
import com.challenge.Literatura.Servicios.ConsultaAPI;
import com.challenge.Literatura.Servicios.ConversionDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsultaAPI consultaAPI = new ConsultaAPI();
    private ConversionDatos conversionDatos = new ConversionDatos();
    private static final String API_URL = "https://gutendex.com/books/";

    private ILibrosRepocitorio libroRepocitorio;
    private IAutorRepocitorio autorRepocitorio;

    public Principal(ILibrosRepocitorio libroRepocitorio, IAutorRepocitorio autorRepocitorio){
        this.libroRepocitorio = libroRepocitorio;
        this.autorRepocitorio = autorRepocitorio;
    }

    private int opcion = 1;

    public void displayMenu(){
        while(opcion != 0){
            System.out.println("---------- Menú ---------- ");
            System.out.println("""
                    1. Buscar libro por título
                    2. Mostrar libros registrados
                    3. Buscar Autor por nombre
                    4. Mostrar autores registrados
                    5. Mostrar autores vivos en un determinado año
                    6. Mostrar libros por idioma
                    0. Salir
                    """);

            try{
                opcion = Integer.parseInt(scanner.nextLine());

                switch(opcion){
                    case 1:
                        buscarLibro();
                        break;
                    case 2:
                        System.out.println("----- Libros Registrados -----\n");
                        buscarTodosLosLibros();
                        break;
                    case 3:
                        buscarAutorPorNombre();
                        break;
                    case 4:
                        System.out.println("----- Autores Registrados -----\n");
                        buscarListaAutores();
                        break;
                    case 5:
                        buscarAutorPorAnio();
                        break;
                    case 6:
                        buscarLibroPorLenguage();
                        break;

                    case 0:
                        System.out.println("¡Gracias por usar nuestra App Literatura!\n");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("opcionión elegida incorrecta. Elija nuevamente.\n");
                }
            }catch(NumberFormatException e){
                System.out.println("Debes seleccionar un número.");
            }
        }
    }

    private void buscarLibro() {
        System.out.println("Escriba el nombre del libro: ");
        String bookName = scanner.nextLine();

        String json = consultaAPI.buscarDatos(API_URL + "?search=" + bookName.replace(" ", "+"));
        JsonDTO results = conversionDatos.convertirDatos(json, JsonDTO.class);

        Optional<Libro> books = results.bookResults().stream()
                .findFirst()
                .map(b -> new Libro(b));

        if (books.isPresent()) {
            Libro Libro = books.get();

            if (Libro.getAutor() != null) {
                Autor Autor = autorRepocitorio.findAutorsByName(Libro.getAutor().getName());

                if (Autor == null) {
                    // Crear y guardar un nuevo Autor si no existe
                    Autor newAuthor = Libro.getAutor();
                    Autor = autorRepocitorio.save(newAuthor);
                }

                try {
                    // Asociar el Autor existente con el libro
                    Libro.setAutor(Autor);
                    libroRepocitorio.save(Libro);
                    System.out.println(Libro);
                } catch (DataIntegrityViolationException e) {
                    System.out.println("El libro ya se encuentra registrado en la base de datos.");
                }
            }
        } else {
            System.out.println("No se encontró el libro: " + bookName);
        }
    }
    private void buscarTodosLosLibros(){
        List<Libro> libros = libroRepocitorio.findAll();
        libros.forEach(System.out::println);
    }

    private void buscarAutorPorNombre(){
        System.out.println("Escribe el nombre del Autor que deseas buscar: ");
        String authorName = scanner.nextLine();

        if(isNumber(authorName)){
            System.out.println("Debes ingresar un nombre, no un número.");
        }else{
            String json = consultaAPI.buscarDatos(API_URL + "?search=" + authorName.replace(" ", "+"));
            JsonDTO results = conversionDatos.convertirDatos(json, JsonDTO.class);

            Optional<AutorDTO> Autor = results.bookResults().stream()
                    .findFirst()
                    .map(a -> new AutorDTO(a.authors().get(0).authorName(), a.authors().get(0).birthYear(), a.authors().get(0).deathYear()));

            if(Autor.isPresent()){
                System.out.println(Autor.get());
            }else{
                System.out.println("No se encontró Autor con el nombre: " + authorName);
            }
        }
    }

    private boolean isNumber(String authorName) {
        try {
            Double.parseDouble(authorName);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void buscarListaAutores(){
        List<Autor> authors = autorRepocitorio.findAll();
        authors.forEach(System.out::println);
    }

    private void buscarAutorPorAnio(){
        System.out.println("Ingrese el año vivo del Autor(es) que desea buscar: ");
        try{
            int year = scanner.nextInt();
            List<Autor> autors = autorRepocitorio.findAutorBetweenYear(year);
            if(autors.isEmpty()){
                System.out.println("No se encontraron registros de autores vivos durante ese año en la base de datos.");
            }else{
                autors.forEach(System.out::println);
            }

        }catch (InputMismatchException e){
            System.out.println("Debes ingresar un año válido.");
        }
        scanner.nextLine();
    }

    private void buscarLibroPorLenguage(){
        System.out.println("Ingrese el idioma que desea buscar: ");
        System.out.println("""
                es -> Español
                en -> Inglés
                fr -> Francés
                pt -> Portugés
                """);

        String language = scanner.nextLine();

        List<Libro> books = libroRepocitorio.findLibroByLanguage(language.toUpperCase());
        if(books.isEmpty()){
            System.out.println("No se encontraron libros en ese idioma");
        }else{
            books.forEach(System.out::println);
        }
    }
}
