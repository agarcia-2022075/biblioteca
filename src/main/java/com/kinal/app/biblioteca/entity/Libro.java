package com.kinal.app.biblioteca.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String titulo;
    @Column (unique = true)
    private String isbn;
    @Column (name = "anio_publicaciones")
    private int anioPublicaciones;
    @Column (name = "copias_disponibles")
    private int copiasDisponibles;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Libro() {
    }

    public Libro(String titulo, String isbn, int anioPublicaciones, int copiasDisponibles, Autor autor, Categoria categoria) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.anioPublicaciones = anioPublicaciones;
        this.copiasDisponibles = copiasDisponibles;
        this.autor = autor;
        this.categoria = categoria;
    }

    public Libro(Long id, String titulo, String isbn, int anioPublicaciones, int copiasDisponibles, Autor autor, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.isbn = isbn;
        this.anioPublicaciones = anioPublicaciones;
        this.copiasDisponibles = copiasDisponibles;
        this.autor = autor;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnioPublicaciones() {
        return anioPublicaciones;
    }

    public void setAnioPublicaciones(int anioPublicaciones) {
        this.anioPublicaciones = anioPublicaciones;
    }

    public int getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public void setCopiasDisponibles(int copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
