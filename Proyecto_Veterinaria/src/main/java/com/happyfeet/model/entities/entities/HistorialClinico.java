package com.happyfeet.model.entities;

import java.time.LocalDate;


public class HistorialClinico {
    private int id;
    private int mascota_id;
    private int veterinario_id;
    private LocalDate fecha_evento;
    private int evento_tipo_id;
    private String descripcion;
    private String diagnostico;
    private String tratamiento_recomendado;
    private Integer producto_id;
    private int cantidad_utilizada;

    public HistorialClinico() {
    }

    public HistorialClinico(int id) {
        this.id = id;
    }

    public HistorialClinico(int mascota_id, int veterinario_id, LocalDate fecha_evento,
                            int evento_tipo_id, String descripcion, String diagnostico,
                            String tratamiento_recomendado) {
        this.mascota_id = mascota_id;
        this.veterinario_id = veterinario_id;
        this.fecha_evento = fecha_evento;
        this.evento_tipo_id = evento_tipo_id;
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
        this.tratamiento_recomendado = tratamiento_recomendado;
    }

    public HistorialClinico(int mascota_id, int veterinario_id, LocalDate fecha_evento,
                            int evento_tipo_id, String descripcion, String diagnostico,
                            String tratamiento_recomendado, Integer producto_id, int cantidad_utilizada) {
        this(mascota_id, veterinario_id, fecha_evento, evento_tipo_id, descripcion, diagnostico, tratamiento_recomendado);
        this.producto_id = producto_id;
        this.cantidad_utilizada = cantidad_utilizada;
    }

    public HistorialClinico(int id, int mascota_id, int veterinario_id, LocalDate fecha_evento,
                            int evento_tipo_id, String descripcion, String diagnostico,
                            String tratamiento_recomendado, Integer producto_id, int cantidad_utilizada) {
        this(mascota_id, veterinario_id, fecha_evento, evento_tipo_id, descripcion, diagnostico,
                tratamiento_recomendado, producto_id, cantidad_utilizada);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMascota_id() {
        return mascota_id;
    }

    public void setMascota_id(int mascota_id) {
        this.mascota_id = mascota_id;
    }

    public int getVeterinario_id() {
        return veterinario_id;
    }

    public void setVeterinario_id(int veterinario_id) {
        this.veterinario_id = veterinario_id;
    }

    public LocalDate getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(LocalDate fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    public int getEvento_tipo_id() {
        return evento_tipo_id;
    }

    public void setEvento_tipo_id(int evento_tipo_id) {
        this.evento_tipo_id = evento_tipo_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento_recomendado() {
        return tratamiento_recomendado;
    }

    public void setTratamiento_recomendado(String tratamiento_recomendado) {
        this.tratamiento_recomendado = tratamiento_recomendado;
    }

    public Integer getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(Integer producto_id) {
        this.producto_id = producto_id;
    }

    public int getCantidad_utilizada() {
        return cantidad_utilizada;
    }

    public void setCantidad_utilizada(int cantidad_utilizada) {
        this.cantidad_utilizada = cantidad_utilizada;
    }

    @Override
    public String toString() {
        return "HistorialClinico{" +
                "id=" + id +
                ", mascota_id=" + mascota_id +
                ", veterinario_id=" + veterinario_id +
                ", fecha_evento=" + fecha_evento +
                ", evento_tipo_id=" + evento_tipo_id +
                ", descripcion='" + descripcion + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento_recomendado='" + tratamiento_recomendado + '\'' +
                ", producto_id=" + producto_id +
                ", cantidad_utilizada=" + cantidad_utilizada +
                '}';
    }
}
