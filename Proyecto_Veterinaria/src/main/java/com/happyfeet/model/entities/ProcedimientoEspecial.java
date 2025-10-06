package com.happyfeet.model.entities;

import com.happyfeet.model.enums.EstadoCita;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class ProcedimientoEspecial {
    private int id;
    private int mascota_id;
    private int veterinario_id;
    private String tipo_procedimiento;
    private String nombre_procedimiento;
    private LocalDateTime fecha_hora;
    private Integer duracion_estimada_minutos;
    private String informacion_preoperatoria;
    private String detalle_procedimiento;
    private String complicaciones;
    private String seguimiento_postoperatorio;
    private LocalDate proximo_control;
    private EstadoCita.Estado estado;
    private double costo_procedimiento;

    public ProcedimientoEspecial() {
    }

    public ProcedimientoEspecial(int id) {
        this.id = id;
    }

    // Constructor sin ID


    public ProcedimientoEspecial(int mascota_id, int veterinario_id, String tipo_procedimiento,
                                 String nombre_procedimiento, LocalDateTime fecha_hora,
                                 Integer duracion_estimada_minutos, String informacion_preoperatoria,
                                 String detalle_procedimiento, String complicaciones, String seguimiento_postoperatorio,
                                 LocalDate proximo_control, EstadoCita.Estado estado, double costo_procedimiento) {
        this.mascota_id = mascota_id;
        this.veterinario_id = veterinario_id;
        this.tipo_procedimiento = tipo_procedimiento;
        this.nombre_procedimiento = nombre_procedimiento;
        this.fecha_hora = fecha_hora;
        this.duracion_estimada_minutos = duracion_estimada_minutos;
        this.informacion_preoperatoria = informacion_preoperatoria;
        this.detalle_procedimiento = detalle_procedimiento;
        this.complicaciones = complicaciones;
        this.seguimiento_postoperatorio = seguimiento_postoperatorio;
        this.proximo_control = proximo_control;
        this.estado = estado;
        this.costo_procedimiento = costo_procedimiento;
    }

    // Constructor completo con ID


    public ProcedimientoEspecial(int id, int mascota_id, int veterinario_id, String tipo_procedimiento,
                                 String nombre_procedimiento, LocalDateTime fecha_hora,
                                 Integer duracion_estimada_minutos, String informacion_preoperatoria,
                                 String detalle_procedimiento, String complicaciones, String seguimiento_postoperatorio,
                                 LocalDate proximo_control, EstadoCita.Estado estado, double costo_procedimiento) {
        this(mascota_id, veterinario_id, tipo_procedimiento, nombre_procedimiento, fecha_hora,
                duracion_estimada_minutos, informacion_preoperatoria, detalle_procedimiento,
                complicaciones, seguimiento_postoperatorio, proximo_control, estado, costo_procedimiento);
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

    public String getTipo_procedimiento() {
        return tipo_procedimiento;
    }

    public void setTipo_procedimiento(String tipo_procedimiento) {
        this.tipo_procedimiento = tipo_procedimiento;
    }

    public String getNombre_procedimiento() {
        return nombre_procedimiento;
    }

    public void setNombre_procedimiento(String nombre_procedimiento) {
        this.nombre_procedimiento = nombre_procedimiento;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public Integer getDuracion_estimada_minutos() {
        return duracion_estimada_minutos;
    }

    public void setDuracion_estimada_minutos(Integer duracion_estimada_minutos) {
        this.duracion_estimada_minutos = duracion_estimada_minutos;
    }

    public String getInformacion_preoperatoria() {
        return informacion_preoperatoria;
    }

    public void setInformacion_preoperatoria(String informacion_preoperatoria) {
        this.informacion_preoperatoria = informacion_preoperatoria;
    }

    public String getDetalle_procedimiento() {
        return detalle_procedimiento;
    }

    public void setDetalle_procedimiento(String detalle_procedimiento) {
        this.detalle_procedimiento = detalle_procedimiento;
    }

    public String getComplicaciones() {
        return complicaciones;
    }

    public void setComplicaciones(String complicaciones) {
        this.complicaciones = complicaciones;
    }

    public String getSeguimiento_postoperatorio() {
        return seguimiento_postoperatorio;
    }

    public void setSeguimiento_postoperatorio(String seguimiento_postoperatorio) {
        this.seguimiento_postoperatorio = seguimiento_postoperatorio;
    }

    public LocalDate getProximo_control() {
        return proximo_control;
    }

    public void setProximo_control(LocalDate proximo_control) {
        this.proximo_control = proximo_control;
    }

    public EstadoCita.Estado getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita.Estado estado) {
        this.estado = estado;
    }

    public double getCosto_procedimiento() {
        return costo_procedimiento;
    }

    public void setCosto_procedimiento(double costo_procedimiento) {
        this.costo_procedimiento = costo_procedimiento;
    }

    @Override
    public String toString() {
        return "ProcedimientoQuirurgico{" +
                "id=" + id +
                ", mascota_id=" + mascota_id +
                ", veterinario_id=" + veterinario_id +
                ", tipo_procedimiento='" + tipo_procedimiento + '\'' +
                ", nombre_procedimiento='" + nombre_procedimiento + '\'' +
                ", fecha_hora=" + fecha_hora +
                ", duracion_estimada_minutos=" + duracion_estimada_minutos +
                ", informacion_preoperatoria='" + informacion_preoperatoria + '\'' +
                ", detalle_procedimiento='" + detalle_procedimiento + '\'' +
                ", complicaciones='" + complicaciones + '\'' +
                ", seguimiento_postoperatorio='" + seguimiento_postoperatorio + '\'' +
                ", proximo_control=" + proximo_control +
                ", estado=" + estado +
                ", costo_procedimiento=" + costo_procedimiento +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProcedimientoEspecial that = (ProcedimientoEspecial) o;
        return id == that.id && mascota_id == that.mascota_id && veterinario_id == that.veterinario_id && Double.compare(costo_procedimiento, that.costo_procedimiento) == 0 && Objects.equals(tipo_procedimiento, that.tipo_procedimiento) && Objects.equals(nombre_procedimiento, that.nombre_procedimiento) && Objects.equals(fecha_hora, that.fecha_hora) && Objects.equals(duracion_estimada_minutos, that.duracion_estimada_minutos) && Objects.equals(informacion_preoperatoria, that.informacion_preoperatoria) && Objects.equals(detalle_procedimiento, that.detalle_procedimiento) && Objects.equals(complicaciones, that.complicaciones) && Objects.equals(seguimiento_postoperatorio, that.seguimiento_postoperatorio) && Objects.equals(proximo_control, that.proximo_control) && estado == that.estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mascota_id, veterinario_id, tipo_procedimiento, nombre_procedimiento, fecha_hora, duracion_estimada_minutos, informacion_preoperatoria, detalle_procedimiento, complicaciones, seguimiento_postoperatorio, proximo_control, estado, costo_procedimiento);
    }
}