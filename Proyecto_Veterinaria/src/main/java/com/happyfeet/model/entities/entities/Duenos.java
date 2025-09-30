package com.happyfeet.model.entities;

import java.util.Objects;

public class Duenos {
    private int id;
    private String nombre_completo;
    private String documento_identidad;
    private String direccion;
    private String telefono;
    private String email;
    private String contacto_emergencia;

    public Duenos() {
    }

    public Duenos(int id) {
        this.id = id;
    }

    public Duenos(String nombre_completo, String documento_identidad, String direccion,
                  String telefono, String email, String contacto_emergencia) {
        this.nombre_completo = nombre_completo;
        this.documento_identidad = documento_identidad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.contacto_emergencia = contacto_emergencia;
    }

    public Duenos(int id, String nombre_completo, String documento_identidad, String direccion,
                  String telefono, String email, String contacto_emergencia) {
        this(nombre_completo, documento_identidad, direccion, telefono, email, contacto_emergencia);
        this.id = id;
    }

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getContacto_emergencia() {
        return contacto_emergencia;
    }

    public void setContacto_emergencia(String contacto_emergencia) {
        this.contacto_emergencia = contacto_emergencia;
    }

    @Override
    public String toString() {
        return "Duenos{" +
                "id=" + id +
                ", nombre_completo='" + nombre_completo + '\'' +
                ", documento_identidad='" + documento_identidad + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", contacto_emergencia='" + contacto_emergencia + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Duenos duenos = (Duenos) o;
        return id == duenos.id &&
                Objects.equals(nombre_completo, duenos.nombre_completo) &&
                Objects.equals(documento_identidad, duenos.documento_identidad) &&
                Objects.equals(direccion, duenos.direccion) &&
                Objects.equals(telefono, duenos.telefono) &&
                Objects.equals(email, duenos.email) &&
                Objects.equals(contacto_emergencia, duenos.contacto_emergencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre_completo, documento_identidad, direccion,
                telefono, email, contacto_emergencia);
    }
}


