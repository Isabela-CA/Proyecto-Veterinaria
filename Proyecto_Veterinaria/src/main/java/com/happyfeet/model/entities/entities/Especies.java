package com.happyfeet.model.entities;


public class Especies {
    private int id;
    private String nombre;

    public Especies() {
    }

    public Especies(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Especies{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
