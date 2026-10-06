package com.kinal.app.biblioteca.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String nombre;

    public Categoria(){}

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public Categoria(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

//Getters and setters
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
}
