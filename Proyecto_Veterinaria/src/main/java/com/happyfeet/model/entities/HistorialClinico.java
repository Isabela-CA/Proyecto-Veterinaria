package com.happyfeet.model.entities;

import java.time.LocalDate;
import java.util.Objects;


public class HistorialClinico {
    private int id;
    private int mascota_id;
    private LocalDate fecha_evento;
    private int evento_tipo_id;
    private String descripcion;
    private String diagnostico;
    private String tratamiento_recomendado;
    private int veterinario_id;
    private int consulta_id;
    private int procedimiento_id;

    public HistorialClinico() {
    }

    public HistorialClinico(int id) {
        this.id = id;
    }

    public HistorialClinico(int mascota_id, LocalDate fecha_evento,
                            int evento_tipo_id, String descripcion, String diagnostico,
                            String tratamiento_recomendado, int veterinario_id, int consulta_id,
                            int procedimiento_id) {
        this.mascota_id = mascota_id;
        this.fecha_evento = fecha_evento;
        this.evento_tipo_id = evento_tipo_id;
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
        this.tratamiento_recomendado = tratamiento_recomendado;
        this.veterinario_id = veterinario_id;
        this.consulta_id = consulta_id;
        this.procedimiento_id = procedimiento_id;
    }

    public HistorialClinico(int id,int mascota_id, LocalDate fecha_evento,
                            int evento_tipo_id, String descripcion,
                            String diagnostico, String tratamiento_recomendado,
                            int veterinario_id, int consulta_id, int procedimiento_id) {
        this(mascota_id, fecha_evento, evento_tipo_id, descripcion, diagnostico, tratamiento_recomendado, veterinario_id, consulta_id, procedimiento_id);
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

    public int getVeterinario_id() {
        return veterinario_id;
    }

    public void setVeterinario_id(int veterinario_id) {
        this.veterinario_id = veterinario_id;
    }

    public int getConsulta_id() {
        return consulta_id;
    }

    public void setConsulta_id(int consulta_id) {
        this.consulta_id = consulta_id;
    }

    public int getProcedimiento_id() {
        return procedimiento_id;
    }

    public void setProcedimiento_id(int procedimiento_id) {
        this.procedimiento_id = procedimiento_id;
    }

    @Override
    public String toString() {
        return "HistorialClinico{" +
                "id=" + id +
                ", mascota_id=" + mascota_id +
                ", fecha_evento=" + fecha_evento +
                ", evento_tipo_id=" + evento_tipo_id +
                ", descripcion='" + descripcion + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamiento_recomendado='" + tratamiento_recomendado + '\'' +
                ", veterinario_id=" + veterinario_id +
                ", consulta_id=" + consulta_id +
                ", procedimiento_id=" + procedimiento_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        HistorialClinico that = (HistorialClinico) o;
        return id == that.id && mascota_id == that.mascota_id && evento_tipo_id == that.evento_tipo_id && veterinario_id == that.veterinario_id && consulta_id == that.consulta_id && procedimiento_id == that.procedimiento_id && Objects.equals(fecha_evento, that.fecha_evento) && Objects.equals(descripcion, that.descripcion) && Objects.equals(diagnostico, that.diagnostico) && Objects.equals(tratamiento_recomendado, that.tratamiento_recomendado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mascota_id, fecha_evento, evento_tipo_id, descripcion, diagnostico, tratamiento_recomendado, veterinario_id, consulta_id, procedimiento_id);
    }
}
