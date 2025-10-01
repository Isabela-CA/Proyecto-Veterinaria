package com.happyfeet.Controller;

import com.happyfeet.Service.CitaService;
import com.happyfeet.model.entities.Cita;

import java.time.LocalDateTime;
import java.util.List;

public class CitaController {
    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    public CitaController() {
        this.citaService = new CitaService();
    }

    public boolean programarCita(int mascotaId, int veterinarioId, LocalDateTime fechaHora, String motivo) {
        try {
            return citaService.programarCita(mascotaId, veterinarioId, fechaHora, motivo);
        } catch (Exception e) {
            System.err.println("Error al programar cita: " + e.getMessage());
            return false;
        }
    }

    public List<Cita> consultarCitas() {
        return citaService.listarTodasLasCitas();
    }

    public Cita consultarCitaPorId(int id) {
        return citaService.consultarCitaPorId(id);
    }

    public boolean cambiarEstadoCita(int citaId, int nuevoEstado) {
        try {
            return citaService.cambiarEstadoCita(citaId, nuevoEstado);
        } catch (Exception e) {
            System.err.println("Error al cambiar estado: " + e.getMessage());
            return false;
        }
    }

    public List<Cita> consultarCitasPorMascota(int mascotaId) {
        return citaService.consultarCitasPorMascota(mascotaId);
    }

    public List<Cita> consultarCitasPorVeterinario(int veterinarioId) {
        return citaService.consultarCitasPorVeterinario(veterinarioId);
    }
}