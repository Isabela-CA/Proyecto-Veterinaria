package com.happyfeet.model.entities;

import com.happyfeet.model.enums.EspecialidadVeterinario;

import java.time.LocalDate;

public class Veterinario {
    private int id;
    private String documento_identidad;
    private String nombre_completo;
    private EspecialidadVeterinario.Especialidad especialidad;
    private String telefono;
    private String email;
    private LocalDate fecha_contratacion;
    private int activo;

    public Veterinario() {
    }

    // Constructor solo con id
    public Veterinario(int id) {
        this.id = id;
    }

    // Constructor sin id (para inserción)
    public Veterinario(String documento_identidad, String nombre_completo,
                       EspecialidadVeterinario.Especialidad especialidad, String telefono,
                       String email, LocalDate fecha_contratacion, int activo) {
        this.documento_identidad = documento_identidad;
        this.nombre_completo = nombre_completo;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
        this.fecha_contratacion = fecha_contratacion;
        this.activo = activo;
    }

    // Constructor completo
    public Veterinario(int id, String documento_identidad, String nombre_completo,
                       EspecialidadVeterinario.Especialidad especialidad, String telefono,
                       String email, LocalDate fecha_contratacion, int activo) {
        this(documento_identidad, nombre_completo, especialidad, telefono, email, fecha_contratacion, activo);
        this.id = id;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento_identidad() {
        return documento_identidad;
    }

    public void setDocumento_identidad(String documento_identidad) {
        this.documento_identidad = documento_identidad;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public EspecialidadVeterinario.Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(EspecialidadVeterinario.Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFecha_contratacion() {
        return fecha_contratacion;
    }

    public void setFecha_contratacion(LocalDate fecha_contratacion) {
        this.fecha_contratacion = fecha_contratacion;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Veterinario{" +
                "id=" + id +
                ", documento_identidad='" + documento_identidad + '\'' +
                ", nombre_completo='" + nombre_completo + '\'' +
                ", especialidad=" + especialidad +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", fecha_contratacion=" + fecha_contratacion +
                ", activo=" + activo +
                '}';
    }
}
