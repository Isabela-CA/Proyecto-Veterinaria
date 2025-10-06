package com.happyfeet.model.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class ConsultaMedica {
    private int id;
    private int mascota_id;
    private int veterinario_id;
    private int cita_id;
    private LocalDateTime fecha_hora;
    private String motivo ;
    private String sintomas;
    private String diagnostico;
    private String recomendaciones ;
    private String observaciones ;
    private double peso_registrado ;
    private double temperatura ;

    public ConsultaMedica() {
    }

    public ConsultaMedica(int id) {
        this.id = id;
    }

    public ConsultaMedica(int mascota_id, int veterinario_id, int cita_id, LocalDateTime fecha_hora,
                          String motivo, String sintomas, String diagnostico, String recomendaciones,
                          String observaciones, double peso_registrado, double temperatura) {
        this.mascota_id = mascota_id;
        this.veterinario_id = veterinario_id;
        this.cita_id = cita_id;
        this.fecha_hora = fecha_hora;
        this.motivo = motivo;
        this.sintomas = sintomas;
        this.diagnostico = diagnostico;
        this.recomendaciones = recomendaciones;
        this.observaciones = observaciones;
        this.peso_registrado = peso_registrado;
        this.temperatura = temperatura;
    }

    public ConsultaMedica(int id, int mascota_id, int veterinario_id, int cita_id, LocalDateTime fecha_hora,
                          String motivo, String sintomas, String diagnostico, String recomendaciones,
                          String observaciones, double peso_registrado, double temperatura) {
        this( mascota_id, veterinario_id, cita_id,fecha_hora,motivo,sintomas,diagnostico,recomendaciones,observaciones,peso_registrado,temperatura);
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

    public int getCita_id() {
        return cita_id;
    }

    public void setCita_id(int cita_id) {
        this.cita_id = cita_id;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getPeso_registrado() {
        return peso_registrado;
    }

    public void setPeso_registrado(double peso_registrado) {
        this.peso_registrado = peso_registrado;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {
        return "ConsultaMedicas{" +
                "id=" + id +
                ", mascota_id=" + mascota_id +
                ", veterinario_id=" + veterinario_id +
                ", cita_id=" + cita_id +
                ", fecha_hora=" + fecha_hora +
                ", motivo='" + motivo + '\'' +
                ", sintomas='" + sintomas + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", recomendaciones='" + recomendaciones + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", peso_registrado=" + peso_registrado +
                ", temperatura=" + temperatura +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConsultaMedica that = (ConsultaMedica) o;
        return id == that.id && mascota_id == that.mascota_id && veterinario_id == that.veterinario_id && cita_id == that.cita_id && Double.compare(peso_registrado, that.peso_registrado) == 0 && Double.compare(temperatura, that.temperatura) == 0 && Objects.equals(fecha_hora, that.fecha_hora) && Objects.equals(motivo, that.motivo) && Objects.equals(sintomas, that.sintomas) && Objects.equals(diagnostico, that.diagnostico) && Objects.equals(recomendaciones, that.recomendaciones) && Objects.equals(observaciones, that.observaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mascota_id, veterinario_id, cita_id, fecha_hora, motivo, sintomas, diagnostico, recomendaciones, observaciones, peso_registrado, temperatura);
    }
}
