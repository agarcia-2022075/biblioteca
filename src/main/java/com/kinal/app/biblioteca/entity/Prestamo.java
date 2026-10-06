package com.kinal.app.biblioteca.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "prestamos")
public class Prestamo {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name = "fecha_prestamo")
    private LocalDate fechaPrestamo;
    @Column (name = "fecha_devolucion_esperada")
    private LocalDate fechaDevolucionEsperada;
    @Column (name = "fecha_devolucion_real")
    private LocalDate fechaDevolucionReal;
    @Column
    private String estado;
    @ManyToOne
    @JoinColumn (name = "socio_id")
    private Socio socio;
    @ManyToOne
    @JoinColumn (name="libro_id")
    private Libro libro;

    public Prestamo() {
    }

    public Prestamo(LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada, LocalDate fechaDevolucionReal, String estado, Socio socio, Libro libro) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estado = estado;
        this.socio = socio;
        this.libro = libro;
    }

    public Prestamo(Long id, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada, LocalDate fechaDevolucionReal, String estado, Socio socio, Libro libro) {
        this.id = id;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.estado = estado;
        this.socio = socio;
        this.libro = libro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
