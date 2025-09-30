package com.happyfeet.model.entities;

import java.time.LocalDateTime;

public class ProcedimientoQuirurgico {
    private int id;
    private int mascota_id;
    private int veterinario_id;
    private Integer cita_id;
    private LocalDateTime fecha_procedimiento;
    private String tipo_procedimiento;
    private String diagnostico;
    private String descripcion_procedimiento;
    private String anestesia_utilizada;
    private String medicacion_prescrita;
    private String cuidados_recomendados;
    private String resultado;

    public ProcedimientoQuirurgico() {
    }

    // Constructores
    public ProcedimientoQuirurgico(int id) {
        this.id = id;
    }

    // Constructor sin cita_id
    public ProcedimientoQuirurgico(int mascota_id, int veterinario_id, LocalDateTime fecha_procedimiento,
                                   String tipo_procedimiento, String diagnostico, String descripcion_procedimiento,
                                   String anestesia_utilizada, String medicacion_prescrita, String cuidados_recomendados,
                                   String resultado) {
        this.mascota_id = mascota_id;
        this.veterinario_id = veterinario_id;
        this.fecha_procedimiento = fecha_procedimiento;
        this.tipo_procedimiento = tipo_procedimiento;
        this.diagnostico = diagnostico;
        this.descripcion_procedimiento = descripcion_procedimiento;
        this.anestesia_utilizada = anestesia_utilizada;
        this.medicacion_prescrita = medicacion_prescrita;
        this.cuidados_recomendados = cuidados_recomendados;
        this.resultado = resultado;
    }
    public ProcedimientoQuirurgico(int mascota_id, int veterinario_id, Integer cita_id, LocalDateTime fecha_procedimiento,
                                   String tipo_procedimiento, String diagnostico, String descripcion_procedimiento,
                                   String anestesia_utilizada, String medicacion_prescrita, String cuidados_recomendados,
                                   String resultado) {
        this(mascota_id, veterinario_id, fecha_procedimiento, tipo_procedimiento, diagnostico, descripcion_procedimiento,
                anestesia_utilizada, medicacion_prescrita, cuidados_recomendados, resultado);
        this.cita_id = cita_id;
    }

    // Constructor completo con ID
    public ProcedimientoQuirurgico(int id, int mascota_id, int veterinario_id, Integer cita_id, LocalDateTime fecha_procedimiento,
                                   String tipo_procedimiento, String diagnostico, String descripcion_procedimiento,
                                   String anestesia_utilizada, String medicacion_prescrita, String cuidados_recomendados,
                                   String resultado) {
        this(mascota_id, veterinario_id, cita_id, fecha_procedimiento, tipo_procedimiento, diagnostico, descripcion_procedimiento,
                anestesia_utilizada, medicacion_prescrita, cuidados_recomendados, resultado);
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

    public Integer getCita_id() {
        return cita_id;
    }

    public void setCita_id(Integer cita_id) {
        this.cita_id = cita_id;
    }

    public LocalDateTime getFecha_procedimiento() {
        return fecha_procedimiento;
    }

    public void setFecha_procedimiento(LocalDateTime fecha_procedimiento) {
        this.fecha_procedimiento = fecha_procedimiento;
    }

    public String getTipo_procedimiento() {
        return tipo_procedimiento;
    }

    public void setTipo_procedimiento(String tipo_procedimiento) {
        this.tipo_procedimiento = tipo_procedimiento;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getDescripcion_procedimiento() {
        return descripcion_procedimiento;
    }

    public void setDescripcion_procedimiento(String descripcion_procedimiento) {
        this.descripcion_procedimiento = descripcion_procedimiento;
    }

    public String getAnestesia_utilizada() {
        return anestesia_utilizada;
    }

    public void setAnestesia_utilizada(String anestesia_utilizada) {
        this.anestesia_utilizada = anestesia_utilizada;
    }

    public String getMedicacion_prescrita() {
        return medicacion_prescrita;
    }

    public void setMedicacion_prescrita(String medicacion_prescrita) {
        this.medicacion_prescrita = medicacion_prescrita;
    }

    public String getCuidados_recomendados() {
        return cuidados_recomendados;
    }

    public void setCuidados_recomendados(String cuidados_recomendados) {
        this.cuidados_recomendados = cuidados_recomendados;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "ProcedimientoQuirurgico{" +
                "id=" + id +
                ", mascota_id=" + mascota_id +
                ", veterinario_id=" + veterinario_id +
                ", cita_id=" + cita_id +
                ", fecha_procedimiento=" + fecha_procedimiento +
                ", tipo_procedimiento='" + tipo_procedimiento + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", descripcion_procedimiento='" + descripcion_procedimiento + '\'' +
                ", anestesia_utilizada='" + anestesia_utilizada + '\'' +
                ", medicacion_prescrita='" + medicacion_prescrita + '\'' +
                ", cuidados_recomendados='" + cuidados_recomendados + '\'' +
                ", resultado='" + resultado + '\'' +
                '}';
    }
}
