package com.happyfeet.model.entities;

import com.happyfeet.model.enums.SexoMascota;

import java.time.LocalDate;
import java.util.Objects;


public class Mascota {
    private int id;
    private int dueno_id;
    private String nombre;
    private int raza_id;
    private LocalDate fecha_nacimiento;
    private SexoMascota.Sexo sexo;
    private double peso_actual;
    private String microchip;
    private String tatuaje;
    private String url_foto;
    private String alergias;
    private String condiciones_preexistentes;


    public Mascota() {
    }

    public Mascota(int id) {
        this.id = id;
    }

    public Mascota(int dueno_id, String nombre, int raza_id, LocalDate fecha_nacimiento,
                   SexoMascota.Sexo sexo, double peso_actual, String microchip,
                   String tatuaje, String url_foto, String alergias, String condiciones_preexistentes) {
        this.dueno_id = dueno_id;
        this.nombre = nombre;
        this.raza_id = raza_id;
        this.fecha_nacimiento = fecha_nacimiento;
        this.sexo = sexo;
        this.peso_actual = peso_actual;
        this.microchip = microchip;
        this.tatuaje = tatuaje;
        this.url_foto = url_foto;
        this.alergias = alergias;
        this.condiciones_preexistentes = condiciones_preexistentes;
    }

    public Mascota(int id, int dueno_id, String nombre, int raza_id,
                   LocalDate fecha_nacimiento, SexoMascota.Sexo sexo,
                   double peso_actual, String microchip, String tatuaje,
                   String url_foto, String alergias, String condiciones_preexistentes) {
        this(dueno_id, nombre, raza_id, fecha_nacimiento, sexo, peso_actual, microchip, tatuaje,
                url_foto, alergias, condiciones_preexistentes);
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDueno_id() {
        return dueno_id;
    }

    public void setDueno_id(int dueno_id) {
        this.dueno_id = dueno_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRaza_id() {
        return raza_id;
    }

    public void setRaza_id(int raza_id) {
        this.raza_id = raza_id;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public SexoMascota.Sexo getSexo() {
        return sexo;
    }

    public void setSexo(SexoMascota.Sexo sexo) {
        this.sexo = sexo;
    }

    public double getPeso_actual() {
        return peso_actual;
    }

    public void setPeso_actual(double peso_actual) {
        this.peso_actual = peso_actual;
    }

    public String getMicrochip() {
        return microchip;
    }

    public void setMicrochip(String microchip) {
        this.microchip = microchip;
    }

    public String getTatuaje() {
        return tatuaje;
    }

    public void setTatuaje(String tatuaje) {
        this.tatuaje = tatuaje;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getCondiciones_preexistentes() {
        return condiciones_preexistentes;
    }

    public void setCondiciones_preexistentes(String condiciones_preexistentes) {
        this.condiciones_preexistentes = condiciones_preexistentes;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", dueno_id=" + dueno_id +
                ", nombre='" + nombre + '\'' +
                ", raza_id=" + raza_id +
                ", fecha_nacimiento=" + fecha_nacimiento +
                ", sexo=" + sexo +
                ", peso_actual=" + peso_actual +
                ", microchip='" + microchip + '\'' +
                ", tatuaje='" + tatuaje + '\'' +
                ", url_foto='" + url_foto + '\'' +
                ", alergias='" + alergias + '\'' +
                ", condiciones_preexistentes='" + condiciones_preexistentes + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Mascota mascota = (Mascota) o;
        return id == mascota.id && dueno_id == mascota.dueno_id && raza_id == mascota.raza_id && Double.compare(peso_actual, mascota.peso_actual) == 0 && Objects.equals(nombre, mascota.nombre) && Objects.equals(fecha_nacimiento, mascota.fecha_nacimiento) && sexo == mascota.sexo && Objects.equals(microchip, mascota.microchip) && Objects.equals(tatuaje, mascota.tatuaje) && Objects.equals(url_foto, mascota.url_foto) && Objects.equals(alergias, mascota.alergias) && Objects.equals(condiciones_preexistentes, mascota.condiciones_preexistentes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dueno_id, nombre, raza_id, fecha_nacimiento, sexo, peso_actual, microchip, tatuaje, url_foto, alergias, condiciones_preexistentes);
    }
}