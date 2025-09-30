package com.happyfeet.model.entities;

public class Raza {
    private int id;
    private int especie_id;
    private String nombre;

    public Raza(){
    }

    // contructor
    public Raza(int id){
        this.id = id;
    }

    public Raza(int id, int especie_id, String nombre) {
        this.id = id;
        this.especie_id = especie_id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEspecie_id() {
        return especie_id;
    }

    public void setEspecie_id(int especie_id) {
        this.especie_id = especie_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Raza{" +
                "id=" + id +
                ", especie_id=" + especie_id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
