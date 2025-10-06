package com.happyfeet.model.entities;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Entidad Veterinario
 * Mapea la tabla veterinarios de la BD
 */
public class Veterinario {
    private int id;
    private String nombre_completo;
    private String documento_identidad;
    private String licencia_profesional;  // CAMPO FALTANTE
    private String especialidad;
    private String telefono;
    private String email;
    private LocalDate fecha_contratacion;
    private int activo;  // 1 = activo, 0 = inactivo

    // Constructores
    public Veterinario() {
    }

    public Veterinario(int id) {
        this.id = id;
    }

    // Constructor sin id (para inserción)
    public Veterinario(String nombre_completo, String documento_identidad,
                       String licencia_profesional, String especialidad,
                       String telefono, String email,
                       LocalDate fecha_contratacion, int activo) {
        this.nombre_completo = nombre_completo;
        this.documento_identidad = documento_identidad;
        this.licencia_profesional = licencia_profesional;
        this.especialidad = especialidad;
        this.telefono = telefono;
        this.email = email;
        this.fecha_contratacion = fecha_contratacion;
        this.activo = activo;
    }

    // Constructor completo con id
    public Veterinario(int id, String nombre_completo, String documento_identidad,
                       String licencia_profesional, String especialidad,
                       String telefono, String email,
                       LocalDate fecha_contratacion, int activo) {
        this(nombre_completo, documento_identidad, licencia_profesional,
                especialidad, telefono, email, fecha_contratacion, activo);
        this.id = id;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getDocumento_identidad() {
        return documento_identidad;
    }

    public void setDocumento_identidad(String documento_identidad) {
        this.documento_identidad = documento_identidad;
    }

    public String getLicencia_profesional() {
        return licencia_profesional;
    }

    public void setLicencia_profesional(String licencia_profesional) {
        this.licencia_profesional = licencia_profesional;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
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
                ", nombre_completo='" + nombre_completo + '\'' +
                ", documento_identidad='" + documento_identidad + '\'' +
                ", licencia_profesional='" + licencia_profesional + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", fecha_contratacion=" + fecha_contratacion +
                ", activo=" + activo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Veterinario that = (Veterinario) o;
        return id == that.id &&
                Objects.equals(documento_identidad, that.documento_identidad) &&
                Objects.equals(licencia_profesional, that.licencia_profesional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documento_identidad, licencia_profesional);
    }
}