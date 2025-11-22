package com.example.glypha_primer_parcial_giardina_saracco.data.model;

public class Fuente {
    private int id;
    private String nombre;
    private String peso;
    private String tamanio;

    //TODO: Agregar categoria

    public Fuente(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}