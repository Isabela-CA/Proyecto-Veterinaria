package com.happyfeet.Controller;

import com.happyfeet.Repository.HistorialClinicoDAO;
import com.happyfeet.Repository.MascotaDAO;
import com.happyfeet.Repository.VeterinarioDAO;
import com.happyfeet.Service.HistorialClinicoService;
import com.happyfeet.model.entities.HistorialClinico;

import java.util.List;

public class HistorialClinicoController {


    private HistorialClinicoService historialService;

    public HistorialClinicoController() {
        // CORREGIDO: Inicializar correctamente el servicio con sus dependencias
        this.historialService = new HistorialClinicoService(
                new HistorialClinicoDAO(),
                new MascotaDAO(),
                new VeterinarioDAO()
        );
    }

    public HistorialClinicoController(HistorialClinicoService historialService) {
        this.historialService = historialService;
    }

    public boolean agregarEventoMedico(HistorialClinico historial) {
        try {
            return historialService.agregarEventoMedico(historial);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error en controller al agregar evento médico: " + e.getMessage());
            return false;
        }
    }

    public List<HistorialClinico> consultarHistorialPorMascota(int mascotaId) {
        try {
            return historialService.consultarHistorialPorMascota(mascotaId);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return List.of();
        } catch (Exception e) {
            System.err.println("Error en controller al consultar historial: " + e.getMessage());
            return List.of();
        }
    }

    public HistorialClinico obtenerHistorial(int id) {
        try {
            return historialService.obtenerHistorial(id);
        } catch (Exception e) {
            System.err.println("Error en controller al obtener historial: " + e.getMessage());
            return null;
        }
    }

    public List<HistorialClinico> listarTodosLosHistoriales() {
        try {
            return historialService.listarTodosLosHistoriales();
        } catch (Exception e) {
            System.err.println("Error en controller al listar historiales: " + e.getMessage());
            return List.of();
        }
    }

    public boolean actualizarHistorial(HistorialClinico historial) {
        try {
            return historialService.actualizarHistorial(historial);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error en controller al actualizar historial: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarVacunacion(int mascotaId, int veterinarioId, String vacuna, String lote) {
        try {
            return historialService.registrarVacunacion(mascotaId, veterinarioId, vacuna, lote);
        } catch (Exception e) {
            System.err.println("Error al registrar vacunación: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarConsultaGeneral(int mascotaId, int veterinarioId, String motivo,
                                            String diagnostico, String tratamiento) {
        try {
            return historialService.registrarConsultaGeneral(mascotaId, veterinarioId, motivo, diagnostico, tratamiento);
        } catch (Exception e) {
            System.err.println("Error al registrar consulta: " + e.getMessage());
            return false;
        }
    }
}

