package com.happyfeet.Controller;

import com.happyfeet.Repository.HistorialClinicoDAO;
import com.happyfeet.Repository.MascotaDAO;
import com.happyfeet.Repository.VeterinarioDAO;
import com.happyfeet.Service.HistorialClinicoService;
import com.happyfeet.model.entities.HistorialClinico;

import java.util.List;

public class HistorialClinicoController {

        private final HistorialClinicoService historialService;

        // Constructor con dependencias inyectadas
        public HistorialClinicoController(HistorialClinicoService historialService) {
            this.historialService = historialService;
        }

        // Constructor sin parámetros que inicializa las dependencias
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

        public boolean agregarEventoMedico(HistorialClinico historial) {
            try {
                return historialService.agregarEventoMedico(historial);
            } catch (Exception e) {
                System.err.println("Error en controller al agregar evento médico: " + e.getMessage());
                return false;
            }
        }

        public List<HistorialClinico> consultarHistorialPorMascota(int mascotaId) {
            try {
                return historialService.consultarHistorialPorMascota(mascotaId);
            } catch (Exception e) {
                System.err.println("Error en controller al consultar historial: " + e.getMessage());
                return List.of();
            }
        }

        public HistorialClinico obtenerHistorial(int id) {
            return historialService.obtenerHistorial(id);
        }

        public List<HistorialClinico> listarTodosLosHistoriales() {
            return historialService.listarTodosLosHistoriales();
        }

        public boolean actualizarHistorial(HistorialClinico historial) {
            try {
                return historialService.actualizarHistorial(historial);
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

