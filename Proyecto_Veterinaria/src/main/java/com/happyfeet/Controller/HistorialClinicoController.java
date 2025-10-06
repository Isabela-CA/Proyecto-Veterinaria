package com.happyfeet.Controller;

import com.happyfeet.Repository.HistorialClinicoDAO;
import com.happyfeet.Repository.MascotaDAO;
import com.happyfeet.Repository.VeterinarioDAO;
import com.happyfeet.Service.HistorialClinicoService;
import com.happyfeet.model.entities.HistorialClinico;

import java.util.List;

/**
 * Controlador para gestionar las operaciones del historial clínico
 * Actúa como intermediario entre la vista y el servicio
 */
public class HistorialClinicoController {

    private final HistorialClinicoService historialService;


    public HistorialClinicoController(HistorialClinicoService historialService) {
        this.historialService = historialService;
    }


    public HistorialClinicoController() {
        HistorialClinicoDAO historialDAO = new HistorialClinicoDAO();
        MascotaDAO mascotaDAO = new MascotaDAO();
        VeterinarioDAO veterinarioDAO = new VeterinarioDAO();

        this.historialService = new HistorialClinicoService(
                historialDAO,
                mascotaDAO,
                veterinarioDAO
        );
    }


    public boolean agregarHistorialClinico(HistorialClinico historial) {
        try {
            return historialService.agregarHistorialClinico(historial);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error en controller al registrar historial: " + e.getMessage());
            e.printStackTrace();
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
            e.printStackTrace();
            return List.of();
        }
    }


    public HistorialClinico buscarHistorialClinicoPorId(int id) {
        try {
            return historialService.buscarHistorialClinicoPorId(id);
        } catch (Exception e) {
            System.err.println("Error en controller al obtener historial: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lista todos los historiales médicos registrados en el sistema
     * @return Lista de todos los historiales
     */
    public List<HistorialClinico> listarTodosLosHistoriales() {
        try {
            return historialService.listarTodosLosHistoriales();
        } catch (Exception e) {
            System.err.println("Error en controller al listar historiales: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Actualiza un registro de historial existente
     * @param historial Objeto HistorialClinico con datos actualizados (debe tener ID válido)
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarHistorial(HistorialClinico historial) {
        try {
            return historialService.actualizarHistorial(historial);
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error en controller al actualizar historial: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}