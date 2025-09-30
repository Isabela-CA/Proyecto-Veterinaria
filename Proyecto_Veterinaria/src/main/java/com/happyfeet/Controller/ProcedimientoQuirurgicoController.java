package com.happyfeet.Controller;

import com.happyfeet.Service.ProcedimientoQuirurgicoService;
import com.happyfeet.model.entities.ProcedimientoQuirurgico;

import java.time.LocalDateTime;
import java.util.List;

public class ProcedimientoQuirurgicoController {

        private ProcedimientoQuirurgicoService procedimientoService;

        public ProcedimientoQuirurgicoController() {
        }

        /**
         * Registra un nuevo procedimiento quirúrgico
         */
        public boolean registrarProcedimiento(int mascotaId, int veterinarioId, Integer citaId,
                                              LocalDateTime fechaProcedimiento, String tipoProcedimiento,
                                              String diagnostico, String descripcion, String anestesia,
                                              String medicacion, String cuidados, String resultado) {
            try {
                return procedimientoService.registrarProcedimiento(mascotaId, veterinarioId, citaId,
                        fechaProcedimiento, tipoProcedimiento, diagnostico, descripcion,
                        anestesia, medicacion, cuidados, resultado);
            } catch (Exception e) {
                System.err.println("Error en controller al registrar procedimiento: " + e.getMessage());
                return false;
            }
        }

        /**
         * Lista todos los procedimientos quirúrgicos
         */
        public List<ProcedimientoQuirurgico> listarTodosLosProcedimientos() {
            return procedimientoService.listarTodosLosProcedimientos();
        }

        /**
         * Busca un procedimiento por ID
         */
        public ProcedimientoQuirurgico buscarProcedimientoPorId(int id) {
            return procedimientoService.buscarProcedimientoPorId(id);
        }

        /**
         * Modifica un procedimiento existente
         */
        public boolean modificarProcedimiento(ProcedimientoQuirurgico procedimiento) {
            try {
                return procedimientoService.modificarProcedimiento(procedimiento);
            } catch (Exception e) {
                System.err.println("Error en controller al modificar procedimiento: " + e.getMessage());
                return false;
            }
        }

        /**
         * Consulta procedimientos por mascota
         */
        public List<ProcedimientoQuirurgico> consultarProcedimientosPorMascota(int mascotaId) {
            return procedimientoService.consultarProcedimientosPorMascota(mascotaId);
        }

        /**
         * Consulta procedimientos por veterinario
         */
        public List<ProcedimientoQuirurgico> consultarProcedimientosPorVeterinario(int veterinarioId) {
            return procedimientoService.consultarProcedimientosPorVeterinario(veterinarioId);
        }

        /**
         * Actualiza el seguimiento postoperatorio
         */
        public boolean actualizarSeguimiento(int procedimientoId, String nuevoResultado,
                                             String cuidadosActualizados, String medicacionActualizada) {
            try {
                return procedimientoService.actualizarSeguimientoPostoperatorio(procedimientoId,
                        nuevoResultado, cuidadosActualizados, medicacionActualizada);
            } catch (Exception e) {
                System.err.println("Error en controller al actualizar seguimiento: " + e.getMessage());
                return false;
            }
        }

        /**
         * Genera reporte de procedimientos
         */
        public void generarReporte(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
            procedimientoService.generarReporteProcedimientos(fechaInicio, fechaFin);
        }

        /**
         * Obtiene los tipos de procedimiento disponibles
         */
        public String[] getTiposProcedimiento() {
            return ProcedimientoQuirurgicoService.TIPOS_PROCEDIMIENTO;
        }
    }
