package com.example.glypha_primer_parcial_giardina_saracco.data.model;

public class Fuente {
    private int id;
    private String nombre;
    private String peso;
    private String tamanio;

    // Constructor vacío requerido por Firestore
    public Fuente() {
    }

    public Fuente(String nombre) {
        this.nombre = nombre;
    }

    public Fuente(int id, String nombre, String peso, String tamanio) {
        this.id = id;
        this.nombre = nombre;
        this.peso = peso;
        this.tamanio = tamanio;
    }

    public Fuente(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters para todas las propiedades
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPeso() {
        return peso;
    }

    public String getTamanio() {
        return tamanio;
    }
}